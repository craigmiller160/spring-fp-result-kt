package io.github.craigmiller160.springarrowkt.wrapper

typealias ResultToWrapperTest = (Any) -> Boolean

private val EITHER_CLASSES =
    listOf("arrow.core.Either", "arrow.core.Either\$Right", "arrow.core.Either\$Left")

val eitherTest: ResultToWrapperTest = { value -> EITHER_CLASSES.contains(value.javaClass.name) }
