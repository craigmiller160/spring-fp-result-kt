package io.github.craigmiller160.fpresultkt.container.service

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.container.config.H2DataSourceOneConfig
import io.github.craigmiller160.fpresultkt.container.domain.ds1.entities.Person
import io.github.craigmiller160.fpresultkt.container.domain.ds1.repositories.PersonRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class SpringNestedTransactionPersonService(
    private val personRepository: PersonRepository,
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

  @Transactional(transactionManager = H2DataSourceOneConfig.TXN_MANAGER)
  fun springNestedSaveFailure(person: Person): Either<Throwable, Person> {
    personRepository.save(person)
    return Either.Left(RuntimeException("Nested Dying"))
  }

  @Transactional(
      transactionManager = H2DataSourceOneConfig.TXN_MANAGER,
      propagation = Propagation.REQUIRES_NEW)
  fun springNestedRequireNewSaveFailure(person: Person): Either<Throwable, Person> {
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

  @Transactional(
      propagation = Propagation.NESTED, transactionManager = H2DataSourceOneConfig.JDBC_TXN_MANAGER)
  fun springNestedJdbcSaveFailure(person: Person): Either<Throwable, Person> {
    val params =
        MapSqlParameterSource()
            .addValue("id", person.id)
            .addValue("name", person.name)
            .addValue("age", person.age)
    jdbcTemplate.update("INSERT INTO person(id, name, age) VALUES (:id, :name, :age)", params)
    return Either.Left(RuntimeException("Nested Dying"))
  }
}
