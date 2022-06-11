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
  fun experiment(joinPoint: JoinPoint, returnValue: Any?) {
    returnValue?.let { nonNullValue ->
      if (nonNullValue is Either.Left<*>) {
        nonNullValue.value?.let { eitherValue ->
          if (eitherValue is Throwable) {
            throw eitherValue
          }
          throw EitherLeftException(eitherValue.toString())
        }
      }
    }
  }
}
