package io.github.craigmiller160.fpresultkt.caching

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class TempPostProcessor : BeanPostProcessor {
  override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
    println("$beanName = ${bean.javaClass.name}")
    return bean
  }
}
