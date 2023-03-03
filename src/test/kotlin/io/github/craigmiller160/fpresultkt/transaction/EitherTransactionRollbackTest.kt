package io.github.craigmiller160.fpresultkt.transaction

import io.github.craigmiller160.fpresultkt.BaseTest
import io.github.craigmiller160.fpresultkt.container.domain.ds1.entities.Person
import io.github.craigmiller160.fpresultkt.container.domain.ds1.repositories.PersonRepository
import io.github.craigmiller160.fpresultkt.container.service.JavaxTransactionPersonService
import io.github.craigmiller160.fpresultkt.container.service.SpringTransactionPersonService
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.UnexpectedRollbackException

class EitherTransactionRollbackTest : BaseTest() {
  @Autowired private lateinit var javaxService: JavaxTransactionPersonService
  @Autowired private lateinit var springService: SpringTransactionPersonService
  @Autowired private lateinit var personRepository: PersonRepository

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
    val result = javaxService.javaxSaveAndCommit(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findById(person.id)).isPresent.get().isEqualTo(person)
  }

  @Test
  fun `javax - rollback if Left returned`() {
    val person = Person(name = "John", age = 30)
    val result = javaxService.javaxSaveAndRollback(person)
    result.shouldBeLeft(RuntimeException("Dying"))
    assertThat(personRepository.findById(person.id)).isEmpty
  }

  @Test
  fun `spring - commit if Right returned`() {
    val person = Person(name = "Jane", age = 10)
    val result = springService.springSaveAndCommit(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findById(person.id)).isPresent.get().isEqualTo(person)
  }

  @Test
  fun `spring - rollback if Left returned`() {
    val person = Person(name = "Sally", age = 40)
    val result = springService.springSaveAndRollback(person)
    result.shouldBeLeft(RuntimeException("Dying"))
    assertThat(personRepository.findById(person.id)).isEmpty
  }

  @Test
  fun `javax - no either, commit if no exception`() {
    val person = Person(name = "Jimmy", age = 50)
    val result = javaxService.javaxNoEitherSaveAndCommit(person)
    assertThat(result).isEqualTo(person)
    assertThat(personRepository.findById(person.id)).isPresent.get().isEqualTo(person)
  }

  @Test
  fun `spring - no either, commit if no exception`() {
    val person = Person(name = "Jimmy", age = 60)
    val result = springService.springNoEitherSaveAndCommit(person)
    assertThat(result).isEqualTo(person)
    assertThat(personRepository.findById(person.id)).isPresent.get().isEqualTo(person)
  }

  @Test
  fun `javax - no either, rollback if exception`() {
    val person = Person(name = "Jimmy", age = 70)
    assertThrows<RuntimeException> { javaxService.javaxNoEitherSaveAndRollback(person) }
    assertThat(personRepository.findById(person.id)).isEmpty
  }

  @Test
  fun `spring - no either, rollback if exception`() {
    val person = Person(name = "Jimmy", age = 80)
    assertThrows<RuntimeException> { springService.springNoEitherSaveAndRollback(person) }
    assertThat(personRepository.findById(person.id)).isEmpty
  }

  @Test
  fun `javax - nested transactional methods, rollback all`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = javaxService.javaxNestedSaveAndRollbackAll(person)
    result.shouldBeLeft(RuntimeException("Nested Dying"))
    assertThat(personRepository.count()).isEqualTo(0)
  }

  @Test
  fun `javax - nested transactional methods, partial rollback, tx type REQUIRED does not support it`() {
    val person = Person(name = "Jimmy", age = 90)
    assertThrows<UnexpectedRollbackException> {
      javaxService.javaxNestedSaveAndPartialRollback(person)
    }
    assertThat(personRepository.count()).isEqualTo(0)
  }

  @Test
  fun `javax - nested transactional methods, partial rollback, tx type REQUIRE_NEW does support it`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = javaxService.javaxNestedRequireNewSaveAndPartialRollback(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findAll()).hasSize(1).contains(person)
  }

  @Test
  fun `javax - nested transactional methods, all commit`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = javaxService.javaxNestedSaveAndCommitAll(person)
    result.isRight()
    assertThat(personRepository.count()).isEqualTo(2)
  }

  @Test
  fun `spring - nested transactional methods, rollback all`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = springService.springNestedSaveAndRollbackAll(person)
    result.shouldBeLeft(RuntimeException("Nested Dying"))
    assertThat(personRepository.count()).isEqualTo(0)
  }

  @Test
  fun `spring - nested transactional methods, partial rollback, propagation REQUIRED does not support it`() {
    val person = Person(name = "Jimmy", age = 90)
    assertThrows<UnexpectedRollbackException> {
      springService.springNestedSaveAndPartialRollback(person)
    }
    assertThat(personRepository.count()).isEqualTo(0)
  }

  @Test
  fun `spring - nested transactional methods, partial rollback, propagation REQUIRE_NEW does support it`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = springService.springNestedRequireNewSaveAndPartialRollback(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findAll()).hasSize(1).contains(person)
  }

  @Test
  fun `spring - nested transactional methods, partial rollback, propagation level NESTED does support it`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = springService.springNestedJdbcSaveAndPartialRollback(person)
    result.shouldBeRight(person)
    assertThat(personRepository.findAll()).hasSize(1).contains(person)
  }

  @Test
  fun `spring - nested transactional methods, all commit`() {
    val person = Person(name = "Jimmy", age = 90)
    val result = springService.springNestedSaveAndCommitAll(person)
    result.isRight()
    assertThat(personRepository.count()).isEqualTo(2)
  }
}
