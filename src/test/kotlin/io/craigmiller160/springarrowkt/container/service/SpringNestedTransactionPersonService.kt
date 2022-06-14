package io.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.config.H2DataSourceOneConfig
import io.craigmiller160.springarrowkt.container.domain.ds1.entities.Person
import io.craigmiller160.springarrowkt.container.domain.ds1.repositories.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class SpringNestedTransactionPersonService(private val personRepository: PersonRepository) {

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveFailure(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveSuccess(person: Person): Either<Throwable, Person> =
      Either.Right(personRepository.save(person))

  @Transactional(
      propagation = Propagation.NESTED, transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveFailureWithCorrectIsolation(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }
}
