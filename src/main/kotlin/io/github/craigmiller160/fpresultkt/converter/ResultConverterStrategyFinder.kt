package io.github.craigmiller160.fpresultkt.converter

object ResultConverterStrategyFinder {
  fun find(value: Any?): ResultConverterStrategy {
    if (value == null) {
      return ResultConverterStrategy.OTHER
    }

    val valueClassName = value.javaClass.name
    return if (EITHER_CLASSES.contains(valueClassName)) {
      ResultConverterStrategy.EITHER
    } else {
      ResultConverterStrategy.OTHER
    }
  }
}
