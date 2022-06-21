package io.github.craigmiller160.springarrowkt.controller

import arrow.core.Either
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import javax.servlet.http.HttpServletResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpOutputMessage
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer

class EitherMethodReturnValueHandler(
    private val delegate: HandlerMethodReturnValueHandler,
    private val messageConverters: List<HttpMessageConverter<*>>
) : HandlerMethodReturnValueHandler {
  override fun supportsReturnType(returnType: MethodParameter): Boolean {
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
        is Unit -> {} // Do nothing
        else -> delegate.handleReturnValue(rightValue, returnType, mavContainer, webRequest)
      }

  private fun handleResponseEntity(
      responseEntity: ResponseEntity<*>,
      webRequest: NativeWebRequest
  ) {
    val response = (webRequest.nativeResponse as HttpServletResponse)
    responseEntity.headers.contentType

    response.status = responseEntity.statusCodeValue
    responseEntity.headers
        .asSequence()
        .flatMap { entry -> entry.value.asSequence().map { entry.key to it } }
        .forEach { (key, value) -> response.addHeader(key, value) }

    if (responseEntity.hasBody() && responseEntity.body !is Unit) {
      @Suppress("UNCHECKED_CAST")
      val converter: HttpMessageConverter<Any> =
          messageConverters.find { converter ->
            converter.canWrite(responseEntity.body!!.javaClass, responseEntity.headers.contentType)
          } as HttpMessageConverter<Any>?
              ?: throw IllegalStateException(
                  "Cannot find HttpMessageConverter for media type ${responseEntity.headers.contentType} and body type ${responseEntity.body!!.javaClass}")

      val outputMessage = EitherHttpOutputMessage()
      converter.write(responseEntity.body!!, responseEntity.headers.contentType, outputMessage)
      response.outputStream.use { stream -> stream.write(outputMessage.bytes) }
    } else {
      response.outputStream.use { it.close() }
    }
  }

  // TODO what if left value is not error?
  private fun handleLeft(leftValue: Any?): Nothing =
      when (leftValue) {
        is Throwable -> throw leftValue
        else -> throw NonThrowableEitherLeftException(leftValue.toString())
      }

  private class EitherHttpOutputMessage : HttpOutputMessage {
    private val innerHeaders = HttpHeaders()
    private val innerOutputStream = ByteArrayOutputStream()
    val bytes: ByteArray
      get() = innerOutputStream.toByteArray()
    override fun getHeaders(): HttpHeaders = innerHeaders
    override fun getBody(): OutputStream = innerOutputStream
  }
}
