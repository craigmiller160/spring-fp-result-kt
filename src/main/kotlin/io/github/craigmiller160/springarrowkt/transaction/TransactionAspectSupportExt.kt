package io.github.craigmiller160.springarrowkt.transaction

import arrow.core.Either
import arrow.core.getOrHandle
import arrow.core.redeemWith
import org.springframework.transaction.NestedTransactionNotSupportedException
import org.springframework.transaction.TransactionStatus

fun TransactionStatus.supportsSavepoints(): Boolean =
    Either.catch { releaseSavepoint("") }
        .redeemWith(
            { ex ->
              when (ex) {
                is NestedTransactionNotSupportedException -> Either.Right(false)
                else -> Either.Left(ex)
              }
            },
            { Either.Right(true) })
        .getOrHandle { throw it }
