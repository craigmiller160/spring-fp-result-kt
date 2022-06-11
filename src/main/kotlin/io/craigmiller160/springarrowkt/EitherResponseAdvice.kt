package io.craigmiller160.springarrowkt

import arrow.core.Either
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Component
@Aspect
class EitherResponseAdvice {
  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  fun controllerMethods() {}

  @AfterReturning("controllerMethods()")
  fun experiment(value: Any?) {
    value?.let { nonNullValue ->
      if (nonNullValue is Either.Left<*>) {
        nonNullValue.value?.let { eitherValue ->
          if (eitherValue is Throwable) {
            throw eitherValue
          }

          // TODO improve this
          throw EitherLeftException(eitherValue.toString())
        }
      }
    }
  }
}
