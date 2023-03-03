package io.github.craigmiller160.fpresultkt.converter.kotlinresult

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.craigmiller160.fpresultkt.converter.CommonResult
import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.CommonResultSuccess
import io.github.craigmiller160.fpresultkt.converter.KOTLIN_RESULT_CLASS
import io.github.craigmiller160.fpresultkt.converter.ResultConverter
import io.github.craigmiller160.fpresultkt.converter.ResultConverterStrategy
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Component

@Component("kotlinResultResultConverter")
@ConditionalOnClass(name = [KOTLIN_RESULT_CLASS])
class KotlinResultResultConverter : ResultConverter {
  override fun convert(value: Any): CommonResult =
      when (val ktResult = value as Result<*, *>) {
        is Ok -> CommonResultSuccess(ktResult.value, ResultConverterStrategy.KOTLIN_RESULT)
        is Err -> CommonResultFailure(ktResult.error, ResultConverterStrategy.KOTLIN_RESULT)
      }
}
