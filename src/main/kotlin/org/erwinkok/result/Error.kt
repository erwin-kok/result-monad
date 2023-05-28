// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

import java.util.stream.Collectors

open class Error(override val message: String) : Exception(message) {
    constructor(t: Throwable) : this("${errorMessage(t)} <${t.javaClass.name}>") {
        this.throwable = t
    }

    constructor(e: Error) : this(e.message) {
        this.throwable = e.throwable
    }

    private var throwable: Throwable? = null
    private val frames = saveStackFrames(2)

    fun stackTrace(): String {
        val sb = StringBuilder()
        if (message.isNotBlank()) {
            sb.appendLine(if (message.isNotBlank()) "Error occurred: $message" else "Error occurred:")
        }
        for (frame in frames) {
            sb.appendLine("\tat ${frame.toStackTraceElement()}")
        }
        val t = throwable
        if (t != null) {
            sb.appendLine("\nCaused by:")
            sb.appendLine(t.stackTraceToString())
        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Error) {
            return super.equals(other)
        }
        return message == other.message
    }

    override fun hashCode(): Int = message.hashCode()
    override fun toString(): String = message
    private fun saveStackFrames(skip: Long) = StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk { i -> i.skip(skip).collect(Collectors.toList()) }
}

fun errorMessage(it: Throwable) = it.message ?: "<unknown>"
