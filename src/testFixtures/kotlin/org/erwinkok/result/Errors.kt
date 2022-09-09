package org.erwinkok.result

fun assertErrorResult(message: String, executable: () -> Result<Any>) {
    executable()
        .onSuccess {
            error("Expected error, but was: $it")
        }
        .onFailure {
            check(message == it.message) { "Error message '${errorMessage(it)}' was not equal to '$message'" }
        }
}

suspend fun coAssertErrorResult(message: String, executable: suspend () -> Result<Any>) {
    executable()
        .onSuccess {
            error("Expected error, but was: $it")
        }
        .onFailure {
            check(message == it.message) { "Error message '${errorMessage(it)}' was not equal to '$message'" }
        }
}

fun assertErrorResultMatches(regex: Regex, executable: () -> Result<Any>) {
    executable()
        .onSuccess {
            error("Expected error, but was: $it")
        }
        .onFailure {
            check(it.message.matches(regex)) { "Error message '${errorMessage(it)}' did not match '$regex'" }
        }
}

suspend fun coAssertErrorResultMatches(regex: Regex, executable: suspend () -> Result<Any>) {
    executable()
        .onSuccess {
            error("Expected error, but was: $it")
        }
        .onFailure {
            check(it.message.matches(regex)) { "Error message '${errorMessage(it)}' did not match '$regex'" }
        }
}

fun <V> Result<V>.expectNoErrors(): V {
    return when (this) {
        is Ok -> value
        is Err -> error("No Errors expected, but got: ${error.message}")
    }
}
