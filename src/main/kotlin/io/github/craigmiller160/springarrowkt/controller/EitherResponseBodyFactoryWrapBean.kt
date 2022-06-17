package io.github.craigmiller160.springarrowkt.controller

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

@Component
class EitherResponseBodyFactoryWrapBean(private val adapter: RequestMappingHandlerAdapter) :
    InitializingBean {

  override fun afterPropertiesSet() {
    val returnValueHandlers = adapter.returnValueHandlers ?: listOf()
    val decoratedReturnValueHandlers = decorateHandlers(returnValueHandlers)
    adapter.returnValueHandlers = decoratedReturnValueHandlers
  }

  private fun decorateHandlers(
      handlers: List<HandlerMethodReturnValueHandler>
  ): List<HandlerMethodReturnValueHandler> =
      handlers.map { handler ->
        when (handler) {
          is RequestResponseBodyMethodProcessor ->
              EitherMethodReturnValueHandler(handler, adapter.messageConverters)
          else -> handler
        }
      }
}
