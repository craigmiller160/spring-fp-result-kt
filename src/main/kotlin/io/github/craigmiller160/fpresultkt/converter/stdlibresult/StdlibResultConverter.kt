package io.github.craigmiller160.fpresultkt.converter.stdlibresult

import io.github.craigmiller160.fpresultkt.converter.CommonResult
import io.github.craigmiller160.fpresultkt.converter.ResultConverter
import io.github.craigmiller160.fpresultkt.converter.STDLIB_RESULT_CLASS
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Component

@Component("stdlibResultConverter")
@ConditionalOnClass(name = [STDLIB_RESULT_CLASS])
class StdlibResultConverter : ResultConverter {
  override fun convert(value: Any): CommonResult {
    TODO("Not yet implemented")
  }
}
