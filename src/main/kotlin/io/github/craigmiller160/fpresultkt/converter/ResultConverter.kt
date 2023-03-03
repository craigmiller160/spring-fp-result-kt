package io.github.craigmiller160.fpresultkt.converter

interface ResultConverter {
  fun convert(value: Any): CommonResult
}
