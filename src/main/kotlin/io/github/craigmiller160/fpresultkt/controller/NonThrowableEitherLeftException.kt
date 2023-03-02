package io.github.craigmiller160.fpresultkt.controller

class NonThrowableEitherLeftException(value: Any?) :
    RuntimeException("Either returned non-Throwable Left value: ${value?.toString()}")
