package io.craigmiller160.springarrowkt

import arrow.core.Either
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Component
@Aspect
class EitherResponseAdvice {
  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  fun restController() {}

  @AfterReturning(value = "restController()", returning = "returnValue")
  fun experiment(joinPoint: JoinPoint, returnValue: Any?): Any? =
      returnValue?.let(::handleReturnValue)

  private fun handleReturnValue(returnValue: Any): Any? =
      when (returnValue) {
        is Either.Left<*> -> handleLeft(returnValue.value)
        is Either.Right<*> -> returnValue.value
        else ->
            throw IllegalStateException(
                "Invalid Either return value type: ${returnValue.javaClass.name}")
      }

  private fun handleLeft(leftValue: Any?): Nothing =
      when (leftValue) {
        null -> throw EitherLeftException("Null value in Left")
        is Throwable -> throw leftValue
        else -> throw EitherLeftException(leftValue.toString())
      }
}
