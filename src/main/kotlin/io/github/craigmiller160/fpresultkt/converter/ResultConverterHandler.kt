package io.github.craigmiller160.fpresultkt.converter

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ResultConverterHandler(
    @Qualifier("stdlibResultConverter") private val stdlibResultConverter: ResultConverter,
    @Qualifier("eitherResultConverter") private val eitherResultConverter: ResultConverter?,
    @Qualifier("kotlinResultResultConverter")
    private val kotlinResultResultConverter: ResultConverter?
) {
  fun convert(value: Any?): CommonResult =
      when (ResultConverterStrategyFinder.find(value)) {
        ResultConverterStrategy.EITHER -> eitherResultConverter!!.convert(value!!)
        ResultConverterStrategy.KOTLIN_RESULT -> kotlinResultResultConverter!!.convert(value!!)
        ResultConverterStrategy.STDLIB_RESULT -> stdlibResultConverter!!.convert(value!!)
        ResultConverterStrategy.OTHER -> CommonResultOther(value)
      }
}
