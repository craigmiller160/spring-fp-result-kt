package io.github.craigmiller160.fpresultkt.container.service

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.container.domain.ds1.entities.Person
import io.github.craigmiller160.fpresultkt.container.domain.ds1.repositories.PersonRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class JakartaNestedTransactionPersonService(private val personRepository: PersonRepository) {
  @Transactional
  fun jakartaNestedSaveFailure(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  fun jakartaNestedRequireNewSaveFailure(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }

  @Transactional
  fun jakartaNestedSaveSuccess(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.save(person))
}
