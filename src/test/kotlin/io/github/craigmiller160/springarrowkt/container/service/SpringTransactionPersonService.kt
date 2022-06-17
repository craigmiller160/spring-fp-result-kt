package io.github.craigmiller160.springarrowkt.container.service

import arrow.core.Either
import arrow.core.redeem
import io.github.craigmiller160.springarrowkt.container.config.H2DataSourceOneConfig
import io.github.craigmiller160.springarrowkt.container.domain.ds1.entities.Person
import io.github.craigmiller160.springarrowkt.container.domain.ds1.repositories.PersonRepository
import java.util.UUID
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class SpringTransactionPersonService(
    private val personRepository: PersonRepository,
    private val nestedService: SpringNestedTransactionPersonService,
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {
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

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.springNestedSaveFailure(newPerson).redeem({ person }, { it })
  }

  @Transactional(
      transactionManager = H2DataSourceOneConfig.TXN_MANAGER,
      propagation = Propagation.REQUIRES_NEW)
  fun springNestedRequireNewSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.springNestedRequireNewSaveFailure(newPerson).redeem({ person }, { it })
  }

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveAndRollbackAll(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.springNestedSaveFailure(newPerson)
  }

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveAndCommitAll(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.springNestedSaveSuccess(newPerson)
  }

  @Transactional(
      propagation = Propagation.NESTED, transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveAndPartialRollbackWithCorrectIsolation(
      person: Person
  ): Either<Throwable, Person> {
    personRepository.save(person)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService
        .springNestedSaveFailureWithCorrectIsolation(newPerson)
        .redeem({ person }, { it })
  }

  @Transactional(
      propagation = Propagation.NESTED, transactionManager = H2DataSourceOneConfig.JDBC_TXN_MANAGER)
  fun springNestedJdbcSaveAndPartialRollback(person: Person): Either<Throwable, Person> {
    val params =
        MapSqlParameterSource()
            .addValue("id", person.id)
            .addValue("name", person.name)
            .addValue("age", person.age)
    jdbcTemplate.update("INSERT INTO person(id, name, age) VALUES (:id, :name, :age)", params)
    val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
    return nestedService.springNestedJdbcSaveFailure(newPerson).redeem({ person }, { it })
  }
}
