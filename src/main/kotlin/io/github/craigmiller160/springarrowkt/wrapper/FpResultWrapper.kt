package io.github.craigmiller160.springarrowkt.wrapper

sealed interface FpResultWrapper

sealed class FpResultSuccess : FpResultWrapper

sealed class FpResultFailure : FpResultWrapper

sealed class FpResultOther : FpResultWrapper
