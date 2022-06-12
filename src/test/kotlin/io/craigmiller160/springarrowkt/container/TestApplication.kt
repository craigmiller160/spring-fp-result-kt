package io.craigmiller160.springarrowkt.container

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["io.craigmiller160.springarrowkt"])
@EnableJpaRepositories
class TestApplication

fun main(args: Array<String>) {
  SpringApplication.run(TestApplication::class.java, *args)
}
