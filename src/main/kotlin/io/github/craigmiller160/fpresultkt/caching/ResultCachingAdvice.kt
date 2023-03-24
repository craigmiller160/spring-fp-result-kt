package io.github.craigmiller160.fpresultkt.caching

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class ResultCachingAdvice(private val context: AnnotationConfigApplicationContext) {
  @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)") fun cacheable() {}

  @Around("cacheable()") fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? {}
}
