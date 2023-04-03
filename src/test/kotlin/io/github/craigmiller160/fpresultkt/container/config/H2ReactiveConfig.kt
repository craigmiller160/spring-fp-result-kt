package io.github.craigmiller160.fpresultkt.container.config

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class H2ReactiveConfig(private val databaseClient: DatabaseClient) {
  @PostConstruct
  fun setupSchema() {
    runBlocking {
      databaseClient
          .sql(
              """
            CREATE TABLE person (
                id UUID NOT NULL,
                name VARCHAR(255),
                age INT,
                PRIMARY KEY (id)
            )
        """.trimIndent())
          .fetch()
          .rowsUpdated()
          .awaitSingle()
    }
  }
}
