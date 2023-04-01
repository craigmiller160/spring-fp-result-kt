package io.github.craigmiller160.fpresultkt.transaction.advice.jakarta

import io.github.craigmiller160.fpresultkt.transaction.support.ResultTransactionSupport
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class JakartaResultTransactionAdvice(
    private val resultTransactionSupport: ResultTransactionSupport
) {
  @Pointcut("@annotation(jakarta.transaction.Transactional)") fun jakartaTransactional() {}

  @Around("jakartaTransactional()")
  fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? =
      resultTransactionSupport.handleResultReturnValue(joinPoint)
}
