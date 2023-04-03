package io.github.craigmiller160.fpresultkt.transaction

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.BaseTest
import io.github.craigmiller160.fpresultkt.transaction.extensions.executeAndAwaitEither
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.shouldBe
import java.util.UUID
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingle
import org.springframework.transaction.reactive.TransactionalOperator

class ReactiveEitherTransactionRollbackTest : BaseTest() {
  @Autowired private lateinit var databaseClient: DatabaseClient
  @Autowired private lateinit var transactionOperator: TransactionalOperator
  @Test
  fun `coroutine - commits the result of the transaction for Right`() {
    runBlocking {
      countRecords().shouldBe(0)
      transactionOperator.executeAndAwaitEither { executeInsert() }.shouldBeRight(1)
      countRecords().shouldBe(1)
    }
  }

  @Test
  fun `coroutine - rolls back the transaction for Left`() {
    TODO()
  }

  @Test
  fun `reactor - commits the result of the transaction for Right`() {
    TODO()
  }

  @Test
  fun `reactor - rolls back the transaction for Left`() {
    TODO()
  }

  private suspend fun countRecords(): Long =
      databaseClient
          .sql("SELECT COUNT(*) AS the_count FROM person")
          .map { row, _ -> row.get("the_count").toString().toLong() }
          .awaitSingle()

  private suspend fun executeInsert(): Either<Throwable, Long> =
      Either.catch {
        databaseClient
            .sql("INSERT INTO person (id, name, age) VALUES (:id, :name, :age)")
            .bind("id", UUID.randomUUID())
            .bind("age", 30)
            .bind("name", "Name-${UUID.randomUUID()}")
            .fetch()
            .rowsUpdated()
            .awaitSingle()
      }
}
