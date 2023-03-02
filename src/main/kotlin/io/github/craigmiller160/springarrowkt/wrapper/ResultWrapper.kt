package io.github.craigmiller160.springarrowkt.wrapper

sealed interface ResultWrapper {
  val value: Any
}

data class ResultSuccess(override val value: Any) : ResultWrapper

data class ResultFailure(override val value: Any) : ResultWrapper

// TODO unclear if this is necessary
data class ResultOther(override val value: Any) : ResultWrapper
