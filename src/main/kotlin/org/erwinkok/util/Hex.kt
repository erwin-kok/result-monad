package org.erwinkok.util

import org.erwinkok.result.Err
import org.erwinkok.result.Ok
import org.erwinkok.result.Result
import org.erwinkok.result.getOrThrow

object Hex {
    private val hextable = "0123456789abcdef".map { it.code.toByte() }

    fun encode(bytes: ByteArray): String {
        val dst = ByteArray(bytes.size * 2)
        for ((i, v) in bytes.withIndex()) {
            dst[i * 2] = hextable[(v.toUByte().toInt() ushr 4)]
            dst[i * 2 + 1] = hextable[(v.toUByte().toInt() and 0x0f)]
        }
        return String(dst)
    }

    fun decode(string: String): Result<ByteArray> {
        val src = string.toByteArray()
        val dst = ByteArray(src.size / 2)
        var srcIndex = 1
        var dstIndex = 0
        while (srcIndex < src.size) {
            val a = fromHexChar(string[srcIndex - 1]) ?: return Err("Hex: invalid byte: ${src[srcIndex - 1]}")
            val b = fromHexChar(string[srcIndex]) ?: return Err("Hex: invalid byte: ${src[srcIndex - 1]}")
            dst[dstIndex] = ((a shl 4) or b).toByte()
            srcIndex += 2
            dstIndex++
        }
        if (src.size % 2 == 1) {
            fromHexChar(src[srcIndex - 1].toInt().toChar()) ?: return Err("Hex: invalid byte: ${src[srcIndex - 1]}")
            return Err("Hex: odd length hex string")
        }
        return Ok(dst)
    }

    fun decodeOrThrow(string: String): ByteArray {
        return decode(string).getOrThrow()
    }

    private fun fromHexChar(c: Char): Int? {
        return when (c) {
            in '0'..'9' -> c - '0'
            in 'a'..'f' -> c - 'a' + 10
            in 'A'..'F' -> c - 'A' + 10
            else -> null
        }
    }
}
