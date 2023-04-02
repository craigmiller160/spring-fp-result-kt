package io.github.craigmiller160.fpresultkt.transaction

import io.github.craigmiller160.fpresultkt.BaseTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator

class ReactiveEitherTransactionRollbackTest : BaseTest() {
  @Autowired private lateinit var databaseClient: DatabaseClient
  @Autowired private lateinit var transactionOperator: TransactionalOperator
  @Test
  fun `coroutine - commits the result of the transaction for Right`() {
    TODO()
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
}
