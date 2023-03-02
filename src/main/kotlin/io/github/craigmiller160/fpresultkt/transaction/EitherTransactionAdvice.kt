package io.github.craigmiller160.fpresultkt.transaction

import arrow.core.Either
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.transaction.interceptor.TransactionAspectSupport

@Aspect
@Component
class EitherTransactionAdvice {
  // TODO add logging here
  @Pointcut("@annotation(javax.transaction.Transactional)") fun javaxTransactional() {}

  @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
  fun springTransactional() {}

  @Around("javaxTransactional() || springTransactional()")
  fun handleEitherReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    val savepoint =
        if (isTransactionActive() &&
            TransactionAspectSupport.currentTransactionStatus().supportsSavepoints()) {
          TransactionAspectSupport.currentTransactionStatus().createSavepoint()
        } else {
          null
        }

    val result = joinPoint.proceed()
    if (result is Either.Left<*> && isTransactionActive()) {
      rollbackTransaction(savepoint)
    }

    return result
  }

  private fun isTransactionActive(): Boolean =
      Either.catch { TransactionAspectSupport.currentTransactionStatus() }.isRight()

  private fun rollbackTransaction(savepoint: Any?) {
    savepoint?.let {
      TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint)
    }
        ?: TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
  }
}
