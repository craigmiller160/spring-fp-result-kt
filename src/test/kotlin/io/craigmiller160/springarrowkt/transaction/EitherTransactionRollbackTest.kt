package io.craigmiller160.springarrowkt.transaction

import io.craigmiller160.springarrowkt.container.TestApplication
import io.craigmiller160.springarrowkt.container.service.PersonService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [TestApplication::class])
@ExtendWith(SpringExtension::class)
class EitherTransactionRollbackTest {
  @Autowired private lateinit var personService: PersonService

  @Test
  fun `commit if Right returned`() {
    TODO()
  }

  @Test
  fun `rollback if Left returned`() {
    TODO("")
  }
}
