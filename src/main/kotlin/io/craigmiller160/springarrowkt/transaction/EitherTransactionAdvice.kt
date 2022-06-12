package io.craigmiller160.springarrowkt.transaction

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class EitherTransactionAdvice {
  @Pointcut("@annotation(javax.transaction.Transactional)") fun javaxTransactional() {}

  @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
  fun springTransactional() {}

  @Around("javaxTransactional() || springTransactional()")
  fun handleEitherReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    println("WORKING") // TODO delete this
    return joinPoint.proceed()
  }
}
