package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import java.lang.reflect.Method
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class ResultCachingAdvice(
    private val context: AbstractApplicationContext,
    private val resultConverterHandler: ResultConverterHandler,
    private val cacheInterceptor: CacheInterceptor
) {
  private val log = LoggerFactory.getLogger(javaClass)
  // TODO delete all of this if it doesn't work
  @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)") fun cacheable() {}

  //    @Around("execution(* org.springframework.cache.interceptor.CacheInterceptor.*(..))")
  fun experiment(joinPoint: ProceedingJoinPoint): Any? {
    println("I AM HERE")
    return joinPoint.proceed()
  }

  //  @Around("cacheable()")
  fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    println("INTERCEPTOR: $cacheInterceptor") // TODO delete this
    val result = joinPoint.proceed()
    val commonResult = resultConverterHandler.convert(result)
    if (commonResult is CommonResultFailure) {
      clearCacheForResult(joinPoint)
    }

    return result
  }

  private fun clearCacheForResult(joinPoint: ProceedingJoinPoint) {
    val method = (joinPoint.signature as MethodSignature).method
    method.getAnnotation(Cacheable::class.java)?.let { clearCacheWithCacheable(method, it) }
  }

  private fun clearCacheWithCacheable(method: Method, cacheable: Cacheable) {
    val cacheManager =
        if (cacheable.cacheManager.isNotBlank()) {
          context.getBean(cacheable.cacheManager) as CacheManager?
        } else {
          context.getBean(CacheManager::class.java)
        }

    if (cacheManager == null) {
      log.warn("Could not find CacheManager to evict cache record for failed result")
      return
    }

    val cacheKey =
        if (cacheable.key.isNotBlank()) {
          // TODO this is going to be really hard to evaluate consistently
          val context = StandardEvaluationContext(method)
          SpelExpressionParser().parseExpression(cacheable.key).getValue(context)
        } else {
          null
        }

    cacheable.cacheNames
        .mapNotNull { cacheManager.getCache(it) }
        .forEach { cache -> cacheKey?.let { cache.evict(it) } ?: run { cache.clear() } }
  }
}
