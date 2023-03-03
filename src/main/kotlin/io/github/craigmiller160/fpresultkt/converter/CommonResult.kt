package io.github.craigmiller160.fpresultkt.converter

sealed interface CommonResult {
  val strategy: ResultConverterStrategy
  val value: Any?
}

data class CommonResultSuccess(
    override val value: Any?,
    override val strategy: ResultConverterStrategy
) : CommonResult

data class CommonResultFailure(
    override val value: Any?,
    override val strategy: ResultConverterStrategy
) : CommonResult

data class CommonResultOther(override val value: Any?) : CommonResult {
  override val strategy: ResultConverterStrategy = ResultConverterStrategy.OTHER
}
