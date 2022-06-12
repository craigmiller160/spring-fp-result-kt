package io.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.Person
import io.craigmiller160.springarrowkt.container.PersonRepository
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
  @Transactional
  fun saveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.save(person))

  @Transactional
  fun saveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Dying"))
  }
}
