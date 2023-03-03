package io.github.craigmiller160.fpresultkt.controller

class NonThrowableResultFailureException(value: Any?) :
    RuntimeException("Either returned non-Throwable Left value: ${value?.toString()}")
