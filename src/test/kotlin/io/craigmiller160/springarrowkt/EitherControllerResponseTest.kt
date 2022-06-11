package io.craigmiller160.springarrowkt

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class EitherControllerResponseTest {
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  fun `unwraps the Right value and returns as 200 response`() {
    TODO("Finish this")
  }

  @Test
  fun `unwraps a ResponseEntity value and returns it`() {
    TODO("Finish this")
  }

  @Test
  fun `unwraps the Left value, throws it as an exception, and handles it via the exception handler`() {
    TODO("Finish this")
  }
}
