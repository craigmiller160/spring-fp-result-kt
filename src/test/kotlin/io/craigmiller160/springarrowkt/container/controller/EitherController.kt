package io.craigmiller160.springarrowkt.container.controller

import arrow.core.Either
import io.craigmiller160.springarrowkt.container.dto.SuccessResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/either")
class EitherController {

  @GetMapping("/success")
  fun success(): Either<Throwable, SuccessResponse> = Either.Right(SuccessResponse("Hello World"))

  @GetMapping("/success/empty") fun successEmptyBody(): Either<Throwable, Unit> = Either.Right(Unit)

  @GetMapping("/success/xml", produces = [MediaType.APPLICATION_XML_VALUE])
  fun successXml(): SuccessResponse = SuccessResponse("Hello World")

  @GetMapping("/response-entity")
  fun responseEntity(): Either<Throwable, ResponseEntity<SuccessResponse>> =
      Either.Right(
          ResponseEntity.status(201).header("Foo", "Bar").body(SuccessResponse("Hello World")))

  @GetMapping("/response-entity/xml")
  fun responseEntityXml(): Either<Throwable, ResponseEntity<SuccessResponse>> =
      Either.Right(
          ResponseEntity.status(201)
              .header("Foo", "Bar")
              .contentType(MediaType.APPLICATION_XML)
              .body(SuccessResponse("Hello World")))

  @GetMapping("/failure")
  fun failure(): Either<Throwable, SuccessResponse> = Either.Left(RuntimeException("Dying"))
}
