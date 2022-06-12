package io.craigmiller160.springarrowkt.transaction

import arrow.core.Either
import javax.sql.DataSource
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.jdbc.datasource.ConnectionHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronizationManager

@Aspect
@Component
class EitherTransactionAdvice {
  @Pointcut("@annotation(javax.transaction.Transactional)") fun javaxTransactional() {}

  @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
  fun springTransactional() {}

  @Around("javaxTransactional() || springTransactional()")
  fun handleEitherReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    val result = joinPoint.proceed()
    if (result is Either.Left<*> && TransactionSynchronizationManager.isActualTransactionActive()) {
      rollbackTransaction()
    }

    return result
  }

  private fun rollbackTransaction() {
    // TODO see if this can be more efficient
    val connectionHolder =
        TransactionSynchronizationManager.getResourceMap()
            .entries
            .find { entry -> entry.key is DataSource }
            ?.value as ConnectionHolder?
            ?: throw IllegalStateException(
                "Cannot find DataSource in TransactionSynchronizationManager resource map")

    connectionHolder.connection.rollback()
  }
}
