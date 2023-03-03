package io.github.craigmiller160.fpresultkt

import io.github.craigmiller160.fpresultkt.container.TestApplication
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [TestApplication::class])
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
abstract class BaseTest
