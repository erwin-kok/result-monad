// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

object Errors {
    val EndOfStream = Error("EndOfStream")
    val UnexpectedEndOfStream = Error("UnexpectedEndOfStream")
    val StreamReset = Error("Stream reset")
    val StreamClosedForReading = Error("Stream closed for reading")
    val StreamClosedForWriting = Error("Stream closed for writing")
    val StreamTimeoutForReading = Error("Timeout reading from stream")
    val StreamTimeoutForWriting = Error("Timeout writing to stream")
}
