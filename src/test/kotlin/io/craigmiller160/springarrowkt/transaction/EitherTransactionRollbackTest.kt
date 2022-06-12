package io.craigmiller160.springarrowkt.transaction

import io.craigmiller160.springarrowkt.container.Person
import io.craigmiller160.springarrowkt.container.PersonRepository
import io.craigmiller160.springarrowkt.container.TestApplication
import io.craigmiller160.springarrowkt.container.service.PersonService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [TestApplication::class])
@ExtendWith(SpringExtension::class)
class EitherTransactionRollbackTest {
  @Autowired private lateinit var personService: PersonService
  @Autowired private lateinit var personRepository: PersonRepository

  @BeforeEach
  fun setup() {
    personRepository.deleteAll()
  }

  @BeforeEach
  fun cleanup() {
    personRepository.deleteAll()
  }

  @Test
  fun `commit if Right returned`() {
    val person = Person(name = "Bob", age = 20)
    val result = personService.saveAndCommit(person)
    // TODO validate that the result is a Right with the same value
    assertNotNull(personRepository.findById(person.id))
  }

  @Test
  fun `rollback if Left returned`() {
    val person = Person(name = "John", age = 30)
    val result = personService.saveAndRollback(person)
    // TODO validate that result is a Left
    assertNull(personRepository.findById(person.id))
  }
}
