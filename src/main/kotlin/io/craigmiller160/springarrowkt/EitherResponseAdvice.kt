package io.craigmiller160.springarrowkt

import arrow.core.Either
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Component
@Aspect
class EitherResponseAdvice {
  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  fun restController() {}

  @Around("restController()")
  fun handleEitherReturnValue(joinPoint: ProceedingJoinPoint): Any? {
    val returnValue = joinPoint.proceed()
    return returnValue?.let(::handleReturnValue)
  }

  private fun handleReturnValue(returnValue: Any): Any? =
      when (returnValue) {
        is Either.Left<*> -> handleLeft(returnValue.value)
        is Either.Right<*> -> returnValue
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
