package org.erwinkok.result

data class Error(override val message: String) : Exception(message) {
    constructor(t: Throwable) : this(errorMessage(t))

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
}

fun errorMessage(it: Throwable) = it.message ?: "<unknown>"
