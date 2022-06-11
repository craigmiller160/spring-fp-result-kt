package io.craigmiller160.springarrowkt

import arrow.core.Either
import org.springframework.core.MethodParameter
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
  ) {
    println("IS RUNNING") // TODO delete this
    if (returnType.declaringClass == Either::class.java) {
      println("IS EITHER") // TODO delete this
    }
    delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest)
  }
}
