package io.github.craigmiller160.springarrowkt.container.testcontainers

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.testcontainers.containers.PostgreSQLContainer

class PostgresTestContainer : PostgreSQLContainer<PostgresTestContainer>("postgres:12.5") {
  init {
    val containerPort = 5432
    val localPort = 5432

    withDatabaseName("springarrowtest")
    withUsername("postgres")
    withPassword("password")
    withExposedPorts(containerPort)
    withReuse(true)
    withCreateContainerCmdModifier { cmd ->
      cmd.withHostConfig(
          HostConfig()
              .withPortBindings(
                  PortBinding(Ports.Binding.bindPort(localPort), ExposedPort(containerPort))))
    }
  }
}
