package io.craigmiller160.springarrowkt.either.controller

class NonThrowableEitherLeftException(value: Any?) :
    RuntimeException("Either returned non-Throwable Left value: ${value?.toString()}")
