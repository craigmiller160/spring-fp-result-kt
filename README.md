# spring-arrow-kt

The bridge between the Spring Framework and Arrow-KT.

## Introduction

For fans of functional programming in Kotlin, `arrow-kt` is one of the best options for adding functional constructs. However, the Spring Framework's OOP design structure introduces friction when trying to leverage its robust feature set while writing functional code. In particular, the advanced "magic" Spring can do was designed with OOP-style code in mind, and with very few exceptions (`vavr`) breaks when used in an FP-style way.

In general, the recommended solution to this ends up being either write OOP-style code for Spring, or use another framework. But what if we could have our cake and eat it too? What if we could leverage the capabilities of the largest and most feature-rich framework in the JVM ecosystem and use FP-style programming patterns? What if we could produce a truly robust hybrid OOP-FP project, leveraging the best pieces of all of it?

That is what this library strives to accomplish. Its goal is to streamline the rough edges and allow developers to use the FP constructs in `arrow-kt` with Spring and everything will just work.

## Installing

This library can be found on Maven Central. The `###` refers to the version number.

<details>
<summary>Maven</summary>

```xml
<dependency>
    <groupId>io.github.craigmiller160</groupId>
    <artifactId>spring-arrow-kt</artifactId>
    <version>###</version>
</dependency>
```

</details>
<details>
<summary>Gradle (Groovy)</summary>

```groovy
implementation 'io.github.craigmiller160:spring-arrow-kt:###'
```

</details>
<details>
<summary>Gradle (Kotlin)</summary>

```kotlin
implementation("io.github.craigmiller160:spring-arrow-kt:###")
```

</details>

## Features

This is still an early build of this library with a narrow feature set. More features can be added in the future.

1. Either
   1. [Controller Responses](#either---controller-responses)
   2. Transaction Rollbacks

## Either - Controller Responses

## Either - Transaction Rollbacks