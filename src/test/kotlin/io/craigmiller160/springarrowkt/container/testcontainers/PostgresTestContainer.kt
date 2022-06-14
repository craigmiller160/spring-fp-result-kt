package io.craigmiller160.springarrowkt.container.testcontainers

import org.testcontainers.containers.PostgreSQLContainer

class PostgresTestContainer : PostgreSQLContainer<PostgresTestContainer>("postgres:12.5") {
  init {
    withDatabaseName("springarrowtest")
    withUsername("postgres")
    withPassword("password")
  }
}
