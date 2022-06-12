package io.craigmiller160.springarrowkt.transaction

import io.craigmiller160.springarrowkt.container.Person
import io.craigmiller160.springarrowkt.container.PersonRepository
import io.craigmiller160.springarrowkt.container.TestApplication
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [TestApplication::class])
@ExtendWith(SpringExtension::class)
class EitherTransactionRollbackTest {
  @Autowired private lateinit var personRepository: PersonRepository
  @Test
  fun practice() {
    val person = Person("Bob", 33)
    personRepository.save(person)
    personRepository.findAll().forEach { println(it) }
  }
}
