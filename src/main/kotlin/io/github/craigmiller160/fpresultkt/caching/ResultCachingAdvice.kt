package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import java.lang.RuntimeException
import java.lang.reflect.Method
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class ResultCachingAdvice(
    private val context: AbstractApplicationContext,
    private val resultConverterHandler: ResultConverterHandler
) {
  private val log = LoggerFactory.getLogger(javaClass)
  @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)") fun cacheable() {}

  @Around("cacheable()")
  fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? {
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

    val context = StandardEvaluationContext(method)
    val cacheKey =
        SpelExpressionParser().parseExpression(cacheable.key).getValue(context)
            ?: RuntimeException("Dying") // TODO better exceptions

    cacheable.cacheNames
        .mapNotNull { cacheManager.getCache(it) }
        .forEach { cache -> cache.evict(cacheKey) }
  }
}
