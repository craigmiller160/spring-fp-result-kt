package io.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.config.H2DataSourceOneConfig
import io.craigmiller160.springarrowkt.container.domain.ds1.entities.Person
import io.craigmiller160.springarrowkt.container.domain.ds1.repositories.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpringTransactionPersonService(private val personRepository: PersonRepository) {
  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springSaveAndCommit(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.saveAndFlush(person))

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springSaveAndRollback(person: Person): Either<Throwable, Person> {
    personRepository.saveAndFlush(person)
    return Either.Left(RuntimeException("Dying"))
  }

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNoEitherSaveAndCommit(person: Person): Person = personRepository.save(person)

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNoEitherSaveAndRollback(person: Person): Person {
    personRepository.save(person)
    throw RuntimeException("Dying")
  }
}
