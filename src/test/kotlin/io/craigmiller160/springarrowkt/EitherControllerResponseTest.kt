package io.craigmiller160.springarrowkt

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class EitherControllerResponseTest {
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  fun `unwraps the Right value and returns as 200 response`() {
    mockMvc.get("/either/success").andExpect {
      status { isOk() }
      content { json("""{"message": "Hello World"}""") }
    }
  }

  @Test
  fun `unwraps the Right value and returns as 200 response, non-JSON content type`() {
    mockMvc.get("/either/success/xml").andExpect {
      status { isOk() }
      content {
        xml(
            """
        <successResponse>
          <message>Hello World</message>
        </successResponse>
      """.trimIndent())
      }
    }
  }

  @Test
  fun `unwraps the Right ResponseEntity value and returns it`() {
    mockMvc.get("/either/response-entity").andExpect {
      status { isEqualTo(201) }
      header { string("Foo", equalTo("Bar")) }
      content { json("""{"message": "Hello World"}""") }
    }
  }

  @Test
  fun `unwraps the Right ResponseEntity value with non-JSON content type and returns it`() {
    TODO("Finish this")
  }

  @Test
  fun `unwraps the Left value, throws it as an exception, and handles it via the exception handler`() {
    mockMvc.get("/either/failure").andExpect {
      status { isInternalServerError() }
      content { json("""{"errorMessage":"Dying"}""") }
    }
  }
}
