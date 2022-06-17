package io.github.craigmiller160.springarrowkt.container.controller

import io.craigmiller160.springarrowkt.container.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerErrorHandler {
  @ExceptionHandler(Exception::class)
  fun exceptionHandler(ex: Exception): ResponseEntity<ErrorResponse> =
      ResponseEntity.status(500).body(ErrorResponse(ex.message ?: ex.javaClass.name))
}
