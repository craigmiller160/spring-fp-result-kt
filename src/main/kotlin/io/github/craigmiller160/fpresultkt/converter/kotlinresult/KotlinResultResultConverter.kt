package io.github.craigmiller160.fpresultkt.converter.kotlinresult

import io.github.craigmiller160.fpresultkt.converter.CommonResult
import io.github.craigmiller160.fpresultkt.converter.KOTLIN_RESULT_CLASS
import io.github.craigmiller160.fpresultkt.converter.ResultConverter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Component

@Component("kotlinResultResultConverter")
@ConditionalOnClass(name = [KOTLIN_RESULT_CLASS])
class KotlinResultResultConverter : ResultConverter {
  override fun convert(value: Any): CommonResult {
    TODO("Not yet implemented")
  }
}
