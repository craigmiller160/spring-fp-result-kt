package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class CacheManagerPostProcessor(private val resultConverterHandler: ResultConverterHandler) :
    BeanPostProcessor {
  override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
    println("BEAN: $beanName = ${bean.javaClass.name}")
    return bean
  }
}
