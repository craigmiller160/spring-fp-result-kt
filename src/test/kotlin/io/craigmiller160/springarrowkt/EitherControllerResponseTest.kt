package io.craigmiller160.springarrowkt

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class EitherControllerResponseTest {
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
