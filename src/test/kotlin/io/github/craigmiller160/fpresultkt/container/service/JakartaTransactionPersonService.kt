package io.github.craigmiller160.fpresultkt.container.service

import arrow.core.Either
import arrow.core.redeem
import io.github.craigmiller160.fpresultkt.container.domain.ds1.entities.Person
import io.github.craigmiller160.fpresultkt.container.domain.ds1.repositories.PersonRepository
import java.util.UUID
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class JakartaTransactionPersonService(
    private val personRepository: PersonRepository,
    private val nestedService: JakartaNestedTransactionPersonService
) {
  @Transactional
  fun jakartaSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.saveAndFlush(person))

  @Transactional
  fun jakartaSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.saveAndFlush(person)
    return Either.Left(RuntimeException("Dying"))
  }

  @Transactional
  fun jakartaNoEitherSaveAndCommit(person: Person): Person = personRepository.save(person)

  @Transactional
  fun jakartaNoEitherSaveAndRollback(person: Person): Person {
    personRepository.save(person)
    throw RuntimeException("Dying")
  }

  @Transactional
  fun jakartaNestedSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedSaveFailure(newPerson).redeem({ person }, { it })
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  fun jakartaNestedRequireNewSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedRequireNewSaveFailure(newPerson).redeem({ person }, { it })
  }

  @Transactional
  fun jakartaNestedSaveAndRollbackAll(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedSaveFailure(newPerson)
  }

  @Transactional
  fun jakartaNestedSaveAndCommitAll(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.javaxNestedSaveSuccess(newPerson)
  }
}
