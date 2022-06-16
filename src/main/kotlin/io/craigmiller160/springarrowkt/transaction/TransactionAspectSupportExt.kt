package io.craigmiller160.springarrowkt.transaction

import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionStatus

fun TransactionStatus.supportsSavepoints(): Boolean =
    when (this) {
      is DefaultTransactionStatus -> TODO()
      else -> false
    }
