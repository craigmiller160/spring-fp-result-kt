package io.github.craigmiller160.fpresultkt.converter

import org.springframework.stereotype.Component

@Component
class ResultConverterHandler(private val eitherResultConverter: ResultConverter) {
  fun convert(value: Any?): CommonResult =
      when (ResultConverterStrategyFinder.find(value)) {
        ResultConverterStrategy.EITHER -> eitherResultConverter.convert(value)
        else -> CommonResultOther(value)
      }
}
