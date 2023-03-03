package io.github.craigmiller160.fpresultkt.container.controller

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.container.dto.SuccessResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/either")
class EitherController {

  @GetMapping("/success")
  fun success(): Either<Throwable, SuccessResponse> = Either.Right(SuccessResponse("Hello World"))

  @GetMapping("/response-entity/no-content")
  fun responseEntityNoContent(): Either<Throwable, ResponseEntity<Unit>> =
      Either.Right(ResponseEntity.noContent().build())

  @GetMapping("/no-content")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun noContent(): Either<Throwable, Unit> = Either.Right(Unit)

  @GetMapping("/success/empty") fun successEmptyBody(): Either<Throwable, Unit> = Either.Right(Unit)

  @GetMapping("/success/xml", produces = [MediaType.APPLICATION_XML_VALUE])
  fun successXml(): SuccessResponse = SuccessResponse("Hello World")

  @GetMapping("/response-entity")
  fun responseEntity(): Either<Throwable, ResponseEntity<SuccessResponse>> =
      Either.Right(
          ResponseEntity.status(201).header("Foo", "Bar").body(SuccessResponse("Hello World")))

  @GetMapping("/response-entity/cookies")
  fun responseEntityCookies(): Either<Throwable, ResponseEntity<SuccessResponse>> =
      Either.Right(
          ResponseEntity.status(201)
              .header("Set-Cookie", "Cookie1=Value1")
              .header("Set-Cookie", "Cookie2=Value2")
              .body(SuccessResponse("Hello World")))

  @GetMapping("/response-entity/empty")
  fun responseEntityEmptyBody(): Either<Throwable, ResponseEntity<Nothing>> =
      Either.Right(ResponseEntity.noContent().header("Foo", "Bar").build())

  @GetMapping("/response-entity/xml")
  fun responseEntityXml(): Either<Throwable, ResponseEntity<SuccessResponse>> =
      Either.Right(
          ResponseEntity.status(201)
              .header("Foo", "Bar")
              .contentType(MediaType.APPLICATION_XML)
              .body(SuccessResponse("Hello World")))

  @GetMapping("/failure")
  fun failure(): Either<Throwable, SuccessResponse> = Either.Left(RuntimeException("Dying"))

  @GetMapping("/normal-value") fun normalValue(): SuccessResponse = SuccessResponse("Hello World")

  @GetMapping("/normal-response-entity")
  fun normalResponseEntity(): ResponseEntity<SuccessResponse> =
      ResponseEntity.status(201).header("Foo", "Bar").body(SuccessResponse("Hello World"))
}
