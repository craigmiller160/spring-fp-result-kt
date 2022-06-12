package io.craigmiller160.springarrowkt.transaction

import io.craigmiller160.springarrowkt.container.Person
import io.craigmiller160.springarrowkt.container.PersonRepository
import io.craigmiller160.springarrowkt.container.TestApplication
import io.craigmiller160.springarrowkt.container.service.PersonService
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
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

  // TODO what about multiple dataSources?

  @BeforeEach
  fun setup() {
    personRepository.deleteAll()
  }

  @AfterEach
  fun cleanup() {
    personRepository.deleteAll()
  }

  @Test
  fun `javax - commit if Right returned`() {
    val person = Person(name = "Bob", age = 20)
    val result = personService.javaxSaveAndCommit(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findById(person.id)).isPresent.get().isEqualTo(person)
  }

  @Test
  fun `javax - rollback if Left returned`() {
    val person = Person(name = "John", age = 30)
    val result = personService.javaxSaveAndRollback(person)
    result.shouldBeLeft(RuntimeException("Dying"))
    assertThat(personRepository.findById(person.id)).isEmpty
  }

  @Test
  fun `spring - commit if Right returned`() {
    val person = Person(name = "Jane", age = 10)
    val result = personService.springSaveAndCommit(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findById(person.id)).isPresent.get().isEqualTo(person)
  }

  @Test
  fun `spring - rollback if Left returned`() {
    val person = Person(name = "Sally", age = 40)
    val result = personService.springSaveAndRollback(person)
    result.shouldBeLeft(RuntimeException("Dying"))
    assertThat(personRepository.findById(person.id)).isEmpty
  }
}
