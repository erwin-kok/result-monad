// Copyright (c) 2023 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

class CombinedError(private val maxErrors: Int = 16) {
    private val errors = mutableListOf<Error>()
    private var skipped: Int = 0

    val hasErrors: Boolean
        get() = errors.isNotEmpty()

    fun recordError(msg: () -> String) {
        recordError(Error(msg()))
    }

    fun recordError(error: Error) {
        if (errors.size >= maxErrors) {
            skipped++
            return
        }
        errors.add(error)
    }

    fun error(): Error {
        val sb = StringBuilder()
        sb.append("The following errors occurred: ")
        for (error in errors) {
            sb.append("\n  * ${error.message}")
        }
        if (skipped > 0) {
            sb.append("\n    ... skipping $skipped errors ...")
        }
        return Error(sb.toString())
    }
}
