package io.craigmiller160.springarrowkt.container.config

import io.craigmiller160.springarrowkt.container.testcontainers.PostgresTestContainer
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Configuration
@Order(Integer.MIN_VALUE)
@Testcontainers
class TestContainersConfig {
  companion object {
    @JvmStatic @Container val postgresContainer = PostgresTestContainer()
  }
}
