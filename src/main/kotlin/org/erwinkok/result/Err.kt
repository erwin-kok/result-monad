// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

class Err(val error: Error) : Result<Nothing>() {
    constructor(error: String) : this(Error(error))
    constructor(error: Throwable) : this(Error(error))

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Err) {
            return super.equals(other)
        }
        return error == other.error
    }

    override fun hashCode(): Int = error.hashCode()
    override fun toString(): String = "Err($error)"
}
