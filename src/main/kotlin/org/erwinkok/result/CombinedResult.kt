// Copyright (c) 2023 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

class CombinedResult<V>(private val cont: Boolean = true, maxErrors: Int = 16) {
    private val errors = CombinedError(maxErrors)
    private val results = mutableListOf<V>()
    private var skipped: Int = 0

    val hasErrors: Boolean
        get() = errors.hasErrors

    fun recordResult(action: () -> Result<V>): CombinedResult<V> {
        if (!errors.hasErrors || cont) {
            val result = action()
            when (result) {
                is Ok -> results.add(result.value)
                is Err -> errors.recordError(result.error)
            }
        }
        return this
    }

    suspend fun recordResultCo(action: suspend () -> Result<V>): CombinedResult<V> {
        if (!errors.hasErrors || cont) {
            val result = action()
            when (result) {
                is Ok -> results.add(result.value)
                is Err -> errors.recordError(result.error)
            }
        }
        return this
    }

    fun result(): Result<List<V>> {
        return if (errors.hasErrors) {
            Err(errors.error())
        } else {
            Ok(results)
        }
    }
}
