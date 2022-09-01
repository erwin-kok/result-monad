# Result Monad

[![License](https://img.shields.io/github/license/erwin-kok/result-monad.svg)](https://github.com/erwin-kok/result-monad/blob/master/LICENSE)


## Introduction

Suppose you have the following method signature:

```kotlin
fun createItem(name: string): String {
    ...
} 
```

This tells you everything to know, right? No, not really. It doesn't say anything about the error conditions it can 
return or throw. Of course, you can add it to the documentation, but the source code itself should be the documentation 
(i.e. if writing documentation as a comment, it can deviate over time from the real code). So the code itself should 
be as readable and understandable as possible. 

In Java (and other languages) you can specify that the method can throw an exception (by adding, for example, a 
`throws IllegalArgumentException`). 

However, when the caller deals with the exception:

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

You don't know which method threw the exception. This is especially cumbersome if, for example, `doSometing()` opens a 
socket and `doSomethingElse()` throws an exception. Normally, `doSomeCleanup()` would close the socket, but if an 
exception was thrown, it would never get there. Of course, you can handle this with a flag `socketOpened` or something
and in the catch-clause you can check this flag and close the socket there. But this looks, in my opinion, rather ugly 
and is error-prone. That is where the result-monad comes in.

## Result Monad

In its generic form, a result monad has two result values: one value for the success case, and an error for the failure case:

```kotlin
fun doSomething(): Result< ...ok value..., ...error...  > {
    ...
}
```
The actual implementation of this method can return either a 'normal' result value, by using `Ok(...)`, or an error 
value, by using `Err(...)`. For example:

```kotlin
fun divideNumbers(a: Int, b: Int): Result<Float, Error> {
    if (b == 0) {
        return Err("Division by zero is not allowed")
    }
    return Ok(a / b)
}
```

Callers can check the returning `Result` value whether an error condition is raised. If not, it can get the ok-value. 
Otherwise, it can handle the error case:

```kotlin
val result = divideNumber(n1, n2)
    .getOrElse {
        // Handle division by zero
        log.error { "Division by zero: ${it.message}" }
        return Err(it) // Return the error to its caller.
    }
log.info { "n1 divided by n2 is $result" }
```

## Chaining calls

Because `Result` has two sub-types, one for the value and one for the error, we can act upon these two different cases.
For example by doing something when `Ok` was returned, and something else when `Err` was returned. `map`, for example, 
applies the given lambda when an `Ok` value was returned, or just passes the error along when `Err` was returned.

```kotlin
val result = divideNumber(n1, n2)
    .map { it * 2 }
```

The example above means that when `divideNumber` returns `Ok` the value is multiplied by 2. If an `Err` was returned, the
multiplication is not performed, but the error original error is returned.

Of course, this can be chained with multiple calls. 

## Inspiration, based on

The idea of a result monad is not new, some other programming languages have something similar already present, like: Go Lang and Rust.

There are already some libraries available in Kotlin, in particular the excellent `kotlin-result` project created by Michael Bull: [kotlin-result](https://github.com/michaelbull/kotlin-result/) where this project is based on. The main difference between his project and this one is that the `error` case always has an `Error` as object:

`kotlin-result`:
```kotlin
fun doSomething(): Result<String, Error> {
    
}
```

This project:
```kotlin
fun doSomething(): Result<String> {
    
}
```
This saves typing.

The `Error` object is a small wrapper around a string and is in itself an Exception. We can instantiate an `Error` object by either a string or an exception. Because of this, the `Err()` can be created more compact:

`kotlin-result`:
```kotlin
return Err(IllegalArgumentException("The argument is wrong!"))
```

This project:
```kotlin
return Err("The argument is wrong!")
```

Remember, an `Error` is a small wrapper around a string, and the error case of the `Result` is always `Error`. This means, the Exception type is lost. However, this can be solved by using a fixed Error:

```kotlin
    val illegalArgumentError = Error("The argument provided is illegal")
    ...
    return Err(illegalArgumentError)
```

...and then check the result for an error, and if an error occurs, check for the specific error:

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


## Contributing

Bug reports and pull requests are welcome on [GitHub](https://github.com).

## License

This project is licensed under the BSD-3-Clause license, see [`LICENSE`](LICENSE) file for more details. 


Since this project is partly based on the awesome `kotlin-result` project, created by Michael Bull, 
I add that license here as well:

```text
Copyright (c) 2017-2022 Michael Bull (https://www.michael-bull.com)

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
```

`kotlin-result` is available at: https://github.com/michaelbull/kotlin-result
