package io.github.craigmiller160.fpresultkt.transaction.extensions

import arrow.core.Either
import org.springframework.transaction.ReactiveTransaction
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import reactor.core.publisher.Flux

suspend fun <E, T> TransactionalOperator.executeAndAwaitEither(
    fn: suspend (ReactiveTransaction) -> Either<E, T>
): Either<E, T> = executeAndAwait { tx ->
  fn(tx).mapLeft { ex ->
    tx.setRollbackOnly()
    ex
  }
}

fun <E, T> TransactionalOperator.execute(
    fn: (ReactiveTransaction) -> Either<E, Flux<T>>
): Either<E, Flux<T>> = TODO()
