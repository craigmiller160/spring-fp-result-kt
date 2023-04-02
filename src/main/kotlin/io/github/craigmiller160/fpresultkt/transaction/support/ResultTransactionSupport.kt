package io.github.craigmiller160.fpresultkt.transaction.support

import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import io.github.craigmiller160.fpresultkt.transaction.extensions.supportsSavepoints
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.interceptor.TransactionAspectSupport

@Component
class ResultTransactionSupport(private val resultConverterHandler: ResultConverterHandler) {
  fun handleResultReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    val savepoint =
        if (isTransactionActive() &&
            TransactionAspectSupport.currentTransactionStatus().supportsSavepoints()) {
          TransactionAspectSupport.currentTransactionStatus().createSavepoint()
        } else {
          null
        }

    val result = joinPoint.proceed()
    val commonResult = resultConverterHandler.convert(result)
    if (commonResult is CommonResultFailure && isTransactionActive()) {
      rollbackTransaction(savepoint)
    }

    return result
  }
}

private fun isTransactionActive(): Boolean =
    runCatching { TransactionAspectSupport.currentTransactionStatus() }.isSuccess

private fun rollbackTransaction(savepoint: Any?) {
  savepoint?.let {
    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint)
  }
      ?: TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
}
