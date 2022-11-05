// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

class Ok<V>(val value: V) : Result<V>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Ok<*>) {
            return super.equals(other)
        }
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = "Ok($value)"
}
