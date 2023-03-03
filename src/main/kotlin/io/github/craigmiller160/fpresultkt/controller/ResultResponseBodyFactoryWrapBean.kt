package io.github.craigmiller160.fpresultkt.controller

import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

@Component
class ResultResponseBodyFactoryWrapBean(
    private val adapter: RequestMappingHandlerAdapter,
    private val resultConverterHandler: ResultConverterHandler
) : InitializingBean {

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
              ResultMethodReturnValueHandler(
                  handler, adapter.messageConverters, resultConverterHandler)
          else -> handler
        }
      }
}
