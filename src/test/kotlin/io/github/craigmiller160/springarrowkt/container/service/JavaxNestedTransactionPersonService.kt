package io.github.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import io.github.craigmiller160.springarrowkt.container.domain.ds1.entities.Person
import io.github.craigmiller160.springarrowkt.container.domain.ds1.repositories.PersonRepository
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class JavaxNestedTransactionPersonService(private val personRepository: PersonRepository) {
  @Transactional
  fun javaxNestedSaveFailure(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  fun javaxNestedRequireNewSaveFailure(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }

  @Transactional
  fun javaxNestedSaveSuccess(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.save(person))
}
