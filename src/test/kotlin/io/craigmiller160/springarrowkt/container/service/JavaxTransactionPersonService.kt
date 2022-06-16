package io.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import arrow.core.redeem
import io.craigmiller160.springarrowkt.container.domain.ds1.entities.Person
import io.craigmiller160.springarrowkt.container.domain.ds1.repositories.PersonRepository
import java.util.UUID
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class JavaxTransactionPersonService(
    private val personRepository: PersonRepository,
    private val nestedService: JavaxNestedTransactionPersonService
) {
  @Transactional
  fun javaxSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.saveAndFlush(person))

  @Transactional
  fun javaxSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.saveAndFlush(person)
    return Either.Left(RuntimeException("Dying"))
  }

  @Transactional
  fun javaxNoEitherSaveAndCommit(person: Person): Person = personRepository.save(person)

  @Transactional
  fun javaxNoEitherSaveAndRollback(person: Person): Person {
    personRepository.save(person)
    throw RuntimeException("Dying")
  }

  @Transactional
  fun javaxNestedSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedSaveFailure(newPerson).redeem({ person }, { it })
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  fun javaxNestedRequireNewSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedRequireNewSaveFailure(newPerson).redeem({ person }, { it })
  }

  @Transactional
  fun javaxNestedSaveAndRollbackAll(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedSaveFailure(newPerson)
  }

  @Transactional
  fun javaxNestedSaveAndCommitAll(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedSaveSuccess(newPerson)
  }
}
