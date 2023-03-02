package io.github.craigmiller160.springarrowkt.wrapper

sealed interface CommonResult {
  val value: Any
}

data class CommonResultSuccess(override val value: Any) : CommonResult

data class CommonResultFailure(override val value: Any) : CommonResult

// TODO unclear if this is necessary
data class CommonResultOther(override val value: Any) : CommonResult
