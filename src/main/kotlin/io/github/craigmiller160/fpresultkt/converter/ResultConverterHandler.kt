package io.github.craigmiller160.fpresultkt.converter

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ResultConverterHandler(
    @Qualifier("stdlibResultConverter") private val stdlibResultConverter: ResultConverter,
    @Qualifier("eitherResultConverter") private val eitherResultConverter: ResultConverter?
) {
  fun convert(value: Any?): CommonResult =
      when (ResultConverterStrategyFinder.find(value)) {
        ResultConverterStrategy.EITHER -> eitherResultConverter!!.convert(value!!)
        else -> CommonResultOther(value)
      }
}
