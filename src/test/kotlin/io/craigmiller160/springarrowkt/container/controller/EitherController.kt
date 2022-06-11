package io.craigmiller160.springarrowkt.container.controller

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.dto.SuccessResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/either")
class EitherController {
  @GetMapping("/success")
  fun success(): Either<Throwable, SuccessResponse> = Either.Right(SuccessResponse("Hello World"))

  @GetMapping("/failure")
  fun failure(): Either<Throwable, SuccessResponse> = Either.Left(RuntimeException("Dying"))
}
