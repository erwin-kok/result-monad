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
and in the catch-clause you can check this flag and close the socket there. But this looks, in my opinion, rather ugly.
