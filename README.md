# Result Monad

[![ci](https://github.com/erwin-kok/result-monad/actions/workflows/ci.yaml/badge.svg)](https://github.com/erwin-kok/result-monad/actions/workflows/ci.yaml)
[![Maven Central](https://img.shields.io/maven-central/v/org.erwinkok.result/result-monad)](https://central.sonatype.com/artifact/org.erwinkok.result/result-monad)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/github/license/erwin-kok/result-monad.svg)](https://github.com/erwin-kok/result-monad/blob/master/LICENSE)

## Introduction

Consider the following function signature:

```kotlin
fun createItem(name: String): String {
    ...
} 
```

While simple, this signature communicates only the success path. It provides no information about possible failure modes, nor whether failure is expressed via return values or exceptions.

Documenting error conditions in comments is brittle: comments drift, but types do not. For long-lived codebases, the type system should describe both success and failure.

This library provides a small, explicit Result type to model this directly.

## Motivation

Exception-based control flow is implicit and non-local. Once execution enters a `try` block, it becomes unclear which operation caused control to jump to a `catch` clause.

```kotlin
try {
    doSomething()
    doSomethingElse()
    finalizeTheThing()
    doSomeCleanup()
} catch (e: IllegalArgumentException) {
    ...
}
```

In this example, it is impossible to determine:

- Which call failed
- Which resources were successfully acquired
- Which cleanup steps are still required

This complicates reasoning about control flow and resource lifetime.

An explicit Result type keeps failure local and visible.

## Result Monad

In its generic form, a result monad represents either:

- Ok(value) on success
- Err(error) on failure

Example:

```kotlin
fun divideNumbers(a: Float, b: Float): Result<Float> {
    if (b == 0) {
        return Err("Division by zero is not allowed")
    }
    return Ok(a / b)
}
```

The caller is required to handle the result explicitly:

```kotlin
val result = divideNumber(n1, n2)
    .getOrElse {
        // Handle division by zero
        log.error { "Division by zero: ${it.message}" }
        return Err(it) // Return the error to its caller.
    }
log.info { "n1 divided by n2 is $result" }
```

Failure is now part of the normal control flow, not an exceptional side-channel.

## Chaining calls

Because Result distinguishes success and failure, operations can be composed safely.

Functions such as map apply transformations only when a value is present:

```kotlin
val result = divideNumber(n1, n2)
    .map { it * 2 }
```

If `divideNumbers` returns `Err`, the transformation is skipped and the error is propagated unchanged.

This enables linear, readable pipelines without nested conditionals or exception handling.

## Installation

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.erwinkok.result:result-monad:$latest")
}
```

## Design Choices

### Fixed Error Type

Unlike many `Result<T, E>` implementations, this library uses a **fixed error type**:

```kotlin
fun doSomething(): Result<String>
```

This reduces verbosity and keeps APIs compact. Error is a lightweight wrapper around a message and is itself an Exception. It can be created from either a string or an existing exception:

```kotlin
return Err("Invalid argument")


return Err(IllegalArgumentException()())
```

### Trade-off

Using a fixed error type means the original exception type is not preserved. When error identity matters, a stable error instance can be used:

```kotlin
val illegalArgumentError = Error("Illegal argument")
```

```kotlin
val result = doSomething()
    .getOrElse {
        if (it === illegalArgumentError) {
            // handle illegal argument
        } else {
            // handle other errors
        }
    }
```

This design favors:

- Explicit control flow
- Simpler APIs
- Reduced generic noise

at the cost of typed error hierarchies.

### Prior Art

The concept of a result type is well established in languages such as Go and Rust.

In Kotlin, this library is inspired by Michael Bull’s excellent [kotlin-result](https://github.com/michaelbull/kotlin-result/).

The primary difference is the use of a fixed error type, trading flexibility for simplicity and consistency.

## License

This project is licensed under the BSD-3-Clause license, see [`LICENSE`](LICENSE) file for more details.

