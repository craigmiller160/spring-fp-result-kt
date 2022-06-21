package io.github.craigmiller160.springarrowkt.controller

import io.github.craigmiller160.springarrowkt.BaseTest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

class EitherControllerResponseTest : BaseTest() {
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  fun `unwraps the Right value and returns as 200 response`() {
    mockMvc.get("/either/success").andExpect {
      status { isOk() }
      content { json("""{"message": "Hello World"}""") }
    }
  }

  @Test
  fun `unwraps the Right value when there is no body and returns as 200 response`() {
    mockMvc.get("/either/success/empty").andExpect {
      status { isOk() }
      header { doesNotExist("content-type") }
      content { string("") }
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
  fun `unwraps the Right ResponseEntity value with no content`() {
    mockMvc.get("/no-content").andExpect { status { isNoContent() } }
  }

  @Test
  fun `unwraps the Right ResponseEntity value and returns multiple cookies`() {
    mockMvc.get("/either/response-entity/cookies").andExpect {
      status { isEqualTo(201) }
      header { stringValues("Set-Cookie", "Cookie1=Value1", "Cookie2=Value2") }
      content { json("""{"message": "Hello World"}""") }
    }
  }

  @Test
  fun `unwraps the Right ResponseEntity value with non-JSON content type and returns it`() {
    mockMvc.get("/either/response-entity/xml").andExpect {
      status { isEqualTo(201) }
      header { string("Foo", equalTo("Bar")) }
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
  fun `unwraps the Right ResponseEntity value with no body and 204 status and returns it`() {
    mockMvc.get("/either/response-entity/empty").andExpect {
      status { isEqualTo(204) }
      header {
        string("Foo", equalTo("Bar"))
        doesNotExist("content-type")
      }
      content { string("") }
    }
  }

  @Test
  fun `unwraps the Left value, throws it as an exception, and handles it via the exception handler`() {
    mockMvc.get("/either/failure").andExpect {
      status { isInternalServerError() }
      content { json("""{"errorMessage":"Dying"}""") }
    }
  }

  @Test
  fun `returns non-either value normally`() {
    mockMvc.get("/either/normal-value").andExpect {
      status { isOk() }
      content { json("""{"message": "Hello World"}""") }
    }
  }

  @Test
  fun `returns non-either ResponseEntity normally`() {
    mockMvc.get("/either/normal-response-entity").andExpect {
      status { isEqualTo(201) }
      header { string("Foo", equalTo("Bar")) }
      content { json("""{"message": "Hello World"}""") }
    }
  }
}
