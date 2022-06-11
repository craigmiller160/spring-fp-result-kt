package io.craigmiller160.springarrowkt

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.http.HttpServletResponse
import org.springframework.core.MethodParameter
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer

class EitherMethodReturnValueHandler(
    private val delegate: HandlerMethodReturnValueHandler,
    private val objectMapper: ObjectMapper
) : HandlerMethodReturnValueHandler {
  // TODO review and clean up all of this
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
        is ResponseEntity<*> -> handleResponseEntity(rightValue, webRequest)
        else -> delegate.handleReturnValue(rightValue, returnType, mavContainer, webRequest)
      }

  private fun handleResponseEntity(
      responseEntity: ResponseEntity<*>,
      webRequest: NativeWebRequest
  ) {
    val response = (webRequest.nativeResponse as HttpServletResponse)
    // TODO need to support non-json responses
    val json = objectMapper.writeValueAsString(responseEntity.body)
    response.status = 201
    response.writer.use { writer -> writer.write(json) }
  }

  // TODO what if left value is not error?
  private fun handleLeft(leftValue: Any?): Nothing =
      when (leftValue) {
        null -> throw EitherLeftException("Null value in Left")
        is Throwable -> throw leftValue
        else -> throw EitherLeftException(leftValue.toString())
      }
}
