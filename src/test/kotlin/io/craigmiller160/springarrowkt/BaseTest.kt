package io.craigmiller160.springarrowkt

import io.craigmiller160.springarrowkt.container.TestApplication
import io.craigmiller160.springarrowkt.container.testcontainers.PostgresTestContainer
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(classes = [TestApplication::class])
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
abstract class BaseTest {
  companion object {
    @JvmStatic @Container val postgresContainer = PostgresTestContainer()
  }
}
