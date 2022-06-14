# spring-arrow-kt

The bridge between the Spring Framework and Arrow-KT.

## Introduction

For fans of functional programming in Kotlin, `arrow-kt` is one of the best options for adding functional constructs. However, the Spring Framework's OOP design structure introduces friction when trying to leverage its robust feature set while writing functional code. In particular, the advanced "magic" Spring can do was designed with OOP-style code in mind, and with very few exceptions (`vavr`) breaks when used in an FP-style way.

In general, the recommended solution to this ends up being either write OOP-style code for Spring, or use another framework. But what if we could have our cake and eat it too? What if we could leverage the capabilities of the largest and most feature-rich framework in the JVM ecosystem and use FP-style programming patterns? What if we could produce a truly robust hybrid OOP-FP project, leveraging the best pieces of all of it?

That is what this library strives to accomplish. Its goal is to streamline the rough edges and allow developers to use the FP constructs in `arrow-kt` with Spring and everything will just work.

## Installing

This library can be found on Maven Central.

<details>
<summary>Maven</summary>

```xml
<dependency>
    <groupId>io.github.craigmiller160</groupId>
    <artifactId>spring-arrow-kt</artifactId>
    <version>1.0.0</version>
</dependency>
```

</details>
<details>
<summary>Gradle (Groovy)</summary>

```groovy
implementation 'io.github.craigmiller160:spring-arrow-kt:1.0.0'
```

</details>
<details>
<summary>Gradle (Kotlin)</summary>

```kotlin
implementation("io.github.craigmiller160:spring-arrow-kt:1.0.0")
```

</details>

Once it is on the classpath, Spring AutoConfiguration will do the rest.

## A Note On Eithers

This library treats the `Either` type exclusively as an error-handling container. It assumes that any `Left` value is an error, even if it is not of type `Throwable`. In the future this may be made configurable to support certain non-error scenarios.

## Features

This is still an early build of this library with a narrow feature set. More features can be added in the future.

1. Either
   1. [Controller Responses](#either---controller-responses)
   2. Transaction Rollbacks

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

NOTE: Nested transaction support, ie a `@Transactional` method that calls an `@Transactional` method in another class, is complicated in Spring. The underlying JPA implementation needs special support to be able to leverage JDBC savepoints to perform partial transaction rollbacks. For the time being, this library treats any `@Transactional` method that returns a `Left` as triggering a full rollback of the entire transaction flow. It is intended to add better support for nested transactions in a future release.