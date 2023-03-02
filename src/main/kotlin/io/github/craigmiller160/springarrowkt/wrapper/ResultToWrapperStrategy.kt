package io.github.craigmiller160.springarrowkt.wrapper

enum class ResultToWrapperStrategy(val test: ResultToWrapperTest) {
  EITHER(test = eitherTest),
  KOTLIN_RESULT(test = { false }),
  OTHER(test = { true }),
  RESULT(test = { false });

  companion object
}
