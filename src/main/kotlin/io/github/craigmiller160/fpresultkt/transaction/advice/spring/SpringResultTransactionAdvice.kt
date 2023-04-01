package io.github.craigmiller160.fpresultkt.transaction.advice.spring

import io.github.craigmiller160.fpresultkt.transaction.support.ResultTransactionSupport
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class SpringResultTransactionAdvice(
    private val resultTransactionSupport: ResultTransactionSupport
) {
  @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
  fun springTransactional() {}

  @Around("springTransactional()")
  fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? =
      resultTransactionSupport.handleResultReturnValue(joinPoint)
}
