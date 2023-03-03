package io.github.craigmiller160.fpresultkt.converter

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.converter.either.EitherResultConverter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ResultConverterHandlerTest {
  private val resultConverterHandler = ResultConverterHandler(EitherResultConverter())
  @Test
  fun `converts Either Right`() {
    val value = "Hello"
    val result = resultConverterHandler.convert(Either.Right(value))
    assertTrue { result is CommonResultSuccess }
    assertEquals(ResultConverterStrategy.EITHER, result.strategy)
    assertEquals(value, result.value)
  }

  @Test
  fun `converts Either Left`() {
    val value = Exception("Dying")
    val result = resultConverterHandler.convert(Either.Left(value))
    assertTrue { result is CommonResultFailure }
    assertEquals(ResultConverterStrategy.EITHER, result.strategy)
    assertEquals(value, result.value)
  }

  @Test
  fun `converters non-result value`() {
    val value = "Hello"
    val result = resultConverterHandler.convert(value)
    assertTrue { result is CommonResultOther }
    assertEquals(ResultConverterStrategy.OTHER, result.strategy)
    assertEquals(value, result.value)
  }

  @Test
  fun `converts null value`() {
    val result = resultConverterHandler.convert(null)
    assertTrue { result is CommonResultOther }
    assertEquals(ResultConverterStrategy.OTHER, result.strategy)
    assertNull(result.value)
  }

  @Test
  fun `converts Kotlin Result Ok`() {
    TODO()
  }

  @Test
  fun `converts Kotlin Result Err`() {
    TODO()
  }

  @Test
  fun `converts stdlib Result success`() {
    TODO()
  }

  @Test
  fun `converts stdlib Result failure`() {
    TODO()
  }
}