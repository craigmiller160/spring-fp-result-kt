package io.github.craigmiller160.fpresultkt.converter

object ResultConverterStrategyFinder {
  fun find(value: Any?): ResultConverterStrategy {
    if (value == null) {
      return ResultConverterStrategy.OTHER
    }

    val valueClassName = value.javaClass.name
    return if (EITHER_CLASSES.contains(valueClassName)) {
      ResultConverterStrategy.EITHER
    } else if (KOTLIN_RESULT_CLASS.contains(valueClassName)) {
      ResultConverterStrategy.KOTLIN_RESULT
    } else if (STDLIB_RESULT_CLASS == valueClassName) {
      ResultConverterStrategy.STDLIB_RESULT
    } else {
      ResultConverterStrategy.OTHER
    }
  }
}
