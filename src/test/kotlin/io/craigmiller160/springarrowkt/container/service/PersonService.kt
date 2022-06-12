package io.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.domain.Person
import io.craigmiller160.springarrowkt.container.domain.PersonRepository
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
  @Transactional
  fun javaxSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.saveAndFlush(person))

  @Transactional
  fun javaxSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.saveAndFlush(person)
    return Either.Left(RuntimeException("Dying"))
  }

  @org.springframework.transaction.annotation.Transactional
  fun springSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.saveAndFlush(person))

  @org.springframework.transaction.annotation.Transactional
  fun springSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.saveAndFlush(person)
    return Either.Left(RuntimeException("Dying"))
  }

  @Transactional
  fun javaxNoEitherSaveAndCommit(person: Person): Person = personRepository.save(person)

  @org.springframework.transaction.annotation.Transactional
  fun springNoEitherSaveAndCommit(person: Person): Person = personRepository.save(person)

  @Transactional
  fun javaxNoEitherSaveAndRollback(person: Person): Person {
    personRepository.save(person)
    throw RuntimeException("Dying")
  }

  @org.springframework.transaction.annotation.Transactional
  fun springNoEitherSaveAndRollback(person: Person): Person {
    personRepository.save(person)
    throw RuntimeException("Dying")
  }
}
