package io.github.craigmiller160.fpresultkt.converter

interface ResultConverter<T> {
  fun convert(value: T): CommonResult
}
