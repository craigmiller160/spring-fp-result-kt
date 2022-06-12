package io.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.Person
import io.craigmiller160.springarrowkt.container.PersonRepository
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
  @Transactional
  fun javaxSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.save(person))

  @Transactional
  fun javaxSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Dying"))
  }

  @org.springframework.transaction.annotation.Transactional
  fun springSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.save(person))

  @org.springframework.transaction.annotation.Transactional
  fun springSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Dying"))
  }
}
