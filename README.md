# spring-fp-result-kt

A simple utility library to support functional programming with "Result" data types in Spring with Kotlin.

## Introduction

A common part of functional programming is working with "Result" data types. These are monads that represent the result of a function that either succeeded or failed. They are an alternative to throwing exceptions and ensure that functions that can fail still have referential transparency.

While Spring supports a variety of programming paradigms, it is still an OOP-first solution. There are certain Spring features that expect an exception to be thrown in order for their behavior to work correctly. While Spring has added support for certain Result types like `io.vavr.control.Try`, there are a variety of additional Result types that are not supported.

This library will configure the consuming Spring project to gracefully handle additional Result types for certain behaviors that normally are only triggered by exception throwing.

## Installing

This library can be found on Maven Central.

<details>
<summary>Maven</summary>

```xml
<dependency>
    <groupId>io.github.craigmiller160</groupId>
    <artifactId>spring-fp-result-kt</artifactId>
    <version>2.0.0</version>
</dependency>
```

</details>
<details>
<summary>Gradle (Groovy)</summary>

```groovy
implementation 'io.github.craigmiller160:spring-fp-result-kt:2.0.0'
```

</details>
<details>
<summary>Gradle (Kotlin)</summary>

```kotlin
implementation("io.github.craigmiller160:spring-fp-result-kt:2.0.0")
```

</details>

Once it is on the classpath, Spring AutoConfiguration will do the rest.

## Supported Result Datatypes

1. Standard Lib Result (`kotlin.Result`)
2. [Michael Bull's Kotlin-Result](https://github.com/michaelbull/kotlin-result) (`com.github.michaelbull.result.Result`)
3. [Arrow-KT's Either](https://arrow-kt.io) (`arrow.core.Either`)














## A Note On Eithers

This library treats the `Either` type exclusively as an error-handling container. It assumes that any `Left` value is an error, even if it is not of type `Throwable`. In the future this may be made configurable to support certain non-error scenarios.

## Features

This is still an early build of this library with a narrow feature set. More features can be added in the future.

1. Either
   1. [Controller Responses](#either---controller-responses)
   2. [Transaction Rollbacks](#either---transaction-rollbacks)

### Either - Controller Responses

When a Spring `RestController` returns a response object, that object is serialized to JSON. Spring only recognizes failures that are thrown exceptions, so an `Either` returned from a controller will simply be serialized and returned as a 200 response.

With this library, an `Either` returned from a controller is automatically unwrapped and handled behind the scenes.

```kotlin
/**
 * If the return value is a Right, the request returns a 200 response with the value of Body
 * If the return value is a Left, the exception will be thrown
 */
@GetMapping("/path")
fun request(): Either<Throwable, Body> = /* ... */
```

For customized responses, an `Either` wrapping around a `ResponseEntity` will also be gracefully handled.

```kotlin
/**
 * If the return value is a Right, the ResponseEntity will be returned.
 * If the return value is a Left, the exception will be thrown.
 */
@GetMapping("/path")
fun request(): Either<Throwable,ResposneEntity<Body>> = /* ... */
```

### Either - Transaction Rollbacks

The default behavior of Spring is to only rollback an `@Transactional` transaction on an exception. However, an `Either` represents a potential fail condition that doesn't involve throwing an exception.

With this library, Spring will recognize `Either`-wrapped failures and perform a rollback.

```kotlin
/**
 * If the return value is a Right, Spring will do its normal Transactional behavior.
 * If the return value is a Left, Spring will roll back the Transaction.
 */
@Transactional
fun operation(): Either<Throwable, Value> = /* ... */
```

**NOTE:** When working with `Either`s as opposed to exceptions, it may seem nature to expect a partial transaction to work. For example:

```kotlin
@Transactional()
fun doSomethingInTransaction(
   person: Person
): Either<Throwable, Person> {
 personRepository.save(person)
 val newPerson = person.copy(id = UUID.randomUUID(), name = "${person.name}-2")
 return nestedService
     .doSomethingElseInTransaction(newPerson)
     .redeem({ person }, { it })
}
```

In this scenario, it may look that the `nestedService.doSomethingElseInTransaction` would rollback if returning a `Left`, whereas `doSomethingInTransaction` would commit since it returns a `Right`. The catch is that Spring's transaction rules apply here. Spring has limited support for nested transactions and partial rollbacks. The point is simple: if your app is configured so a nested transaction would work with exceptions & try/catch, it'll work with `Either`s via this library. If a nested transaction would not work with exceptions & try/catch, then it still will not work with this library.