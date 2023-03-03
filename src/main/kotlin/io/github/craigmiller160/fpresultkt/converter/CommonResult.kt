package io.github.craigmiller160.fpresultkt.converter

sealed interface CommonResult {
  val value: Any?
}

data class CommonResultSuccess(override val value: Any?) : CommonResult

data class CommonResultFailure(override val value: Any?) : CommonResult

data class CommonResultOther(override val value: Any?) : CommonResult
