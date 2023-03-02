package io.github.craigmiller160.springarrowkt.wrapper

sealed interface FpResultWrapper {
  val value: Any
}

data class FpResultSuccess(override val value: Any) : FpResultWrapper

data class FpResultFailure(override val value: Any) : FpResultWrapper

data class FpResultOther(override val value: Any) : FpResultWrapper
