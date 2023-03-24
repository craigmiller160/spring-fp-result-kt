package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class ResultCachingAdvice(
    private val context: AbstractApplicationContext,
    private val resultConverterHandler: ResultConverterHandler
) {
  @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)") fun cacheable() {}

  @Around("cacheable()")
  fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    val result = joinPoint.proceed()
    val commonResult = resultConverterHandler.convert(result)
    if (commonResult is CommonResultFailure) {
      TODO()
    }

    return result
  }

  private fun clearCacheForResult(joinPoint: ProceedingJoinPoint) {
    (joinPoint.signature as MethodSignature).method.getAnnotation(Cacheable::class.java)?.let {
      clearCacheWithCacheable(it)
    }
  }

  private fun clearCacheWithCacheable(cacheable: Cacheable) {}
}
