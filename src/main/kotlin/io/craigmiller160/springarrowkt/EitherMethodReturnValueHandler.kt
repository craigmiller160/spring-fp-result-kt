package io.craigmiller160.springarrowkt

import arrow.core.Either
import org.springframework.core.MethodParameter
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer

class EitherMethodReturnValueHandler(private val delegate: HandlerMethodReturnValueHandler) :
    HandlerMethodReturnValueHandler {
  override fun supportsReturnType(returnType: MethodParameter): Boolean {
    // TODO consider enhancing this
    return delegate.supportsReturnType(returnType)
  }

  override fun handleReturnValue(
      returnValue: Any?,
      returnType: MethodParameter,
      mavContainer: ModelAndViewContainer,
      webRequest: NativeWebRequest
  ) =
      when (returnValue) {
        is Either.Left<*> -> handleLeft(returnValue.value)
        is Either.Right<*> -> handleRight(returnValue.value, returnType, mavContainer, webRequest)
        else -> delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest)
      }

  private fun handleRight(
      rightValue: Any?,
      returnType: MethodParameter,
      mavContainer: ModelAndViewContainer,
      webRequest: NativeWebRequest
  ) =
      when (rightValue) {
        is ResponseEntity<*> -> TODO("Not implemented")
        else -> delegate.handleReturnValue(rightValue, returnType, mavContainer, webRequest)
      }

  private fun handleLeft(leftValue: Any?): Nothing =
      when (leftValue) {
        null -> throw EitherLeftException("Null value in Left")
        is Throwable -> throw leftValue
        else -> throw EitherLeftException(leftValue.toString())
      }
}
