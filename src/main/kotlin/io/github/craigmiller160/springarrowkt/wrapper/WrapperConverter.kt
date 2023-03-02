package io.github.craigmiller160.springarrowkt.wrapper

interface WrapperConverter {
  fun canConvert(clazz: Class<*>): Boolean
  fun convert(value: Any): FpResultWrapper
}
