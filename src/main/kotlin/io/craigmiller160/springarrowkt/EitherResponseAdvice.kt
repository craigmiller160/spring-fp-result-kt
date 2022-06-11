package io.craigmiller160.springarrowkt

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Component
@Aspect
class EitherResponseAdvice {
  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  fun controllerMethods() {}

  @After("controllerMethods()")
  fun experiment() {
    println("THIS IS WORKING") // TODO delete this
  }
}
