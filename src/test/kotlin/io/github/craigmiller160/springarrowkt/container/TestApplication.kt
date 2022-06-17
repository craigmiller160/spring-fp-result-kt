package io.github.craigmiller160.springarrowkt.container

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["io.github.craigmiller160.springarrowkt.container"])
class TestApplication

fun main(args: Array<String>) {
  SpringApplication.run(TestApplication::class.java, *args)
}
