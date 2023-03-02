package io.github.craigmiller160.fpresultkt.converter

import org.springframework.stereotype.Component

@Component
class ResultConverterHandler {
  fun convert(value: Any): CommonResult {
    val strategy = ResultConverterStrategyFinder.find(value)
    TODO()
  }
}
