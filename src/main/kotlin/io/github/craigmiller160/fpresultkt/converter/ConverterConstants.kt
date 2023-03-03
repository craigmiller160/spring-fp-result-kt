package io.github.craigmiller160.fpresultkt.converter

internal const val EITHER_CLASS = "arrow.core.Either"
internal const val EITHER_RIGHT_CLASS = "arrow.core.Either\$Right"
internal const val EITHER_LEFT_CLASS = "arrow.core.Either\$Left"
internal val EITHER_CLASSES = listOf(EITHER_CLASS, EITHER_RIGHT_CLASS, EITHER_LEFT_CLASS)

internal const val STDLIB_RESULT_CLASS = "kotlin.Result"

internal const val KOTLIN_RESULT_CLASS = "com.github.michaelbull.result.Result"
internal const val KOTLIN_RESULT_OK_CLASS = "com.github.michaelbull.result.Ok"
internal const val KOTLIN_RESULT_ERR_CLASS = "com.github.michaelbull.result.Err"
internal val KOTLIN_RESULT_CLASSES =
    listOf(KOTLIN_RESULT_CLASS, KOTLIN_RESULT_OK_CLASS, KOTLIN_RESULT_ERR_CLASS)
