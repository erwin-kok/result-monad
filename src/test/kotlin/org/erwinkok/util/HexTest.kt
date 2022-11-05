// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.util

import org.erwinkok.result.Error
import org.erwinkok.result.assertErrorResult
import org.erwinkok.result.expectNoErrors
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Random

internal class HexTest {
    @Test
    fun loop() {
        val random = Random()
        for (i in 0..1024) {
            val bytes = ByteArray(1024)
            random.nextBytes(bytes)
            val encoded = Hex.encode(bytes)
            assertArrayEquals(bytes, Hex.decode(encoded).expectNoErrors())
        }
    }

    @Test
    fun `sample encode`() {
        assertEquals("010203045678fe", Hex.encode(byteArrayOf(1, 2, 3, 4, 0x56, 0x78, 254.toByte())))
    }

    @Test
    fun `sample decode`() {
        assertArrayEquals(byteArrayOf(8, 9, 170.toByte(), 255.toByte(), 34, 0x77, 0, 17), Hex.decode("0809aaff22770011").expectNoErrors())
    }

    @Test
    fun `sample decode or throw`() {
        assertArrayEquals(byteArrayOf(0x73, 0x59, 22, 0xaa.toByte(), 0x49, 0x56, 18, 0xfd.toByte()), Hex.decodeOrThrow("735916aa495612FD"))
    }

    @Test
    fun `sample decode error too short`() {
        assertErrorResult("Hex: odd length hex string") { Hex.decode("11223") }
    }

    @Test
    fun `sample decode or throw error too short`() {
        assertEquals("Hex: odd length hex string", assertThrows<Error> { Hex.decodeOrThrow("11223") }.message)
    }

    @Test
    fun `sample decode error invalid char`() {
        assertErrorResult("Hex: invalid byte: 122") { Hex.decode("zzyy") }
    }

    @Test
    fun `sample decode or throw error invalid char`() {
        assertEquals("Hex: invalid byte: 91", assertThrows<Error> { Hex.decodeOrThrow("[*^]") }.message)
    }
}
