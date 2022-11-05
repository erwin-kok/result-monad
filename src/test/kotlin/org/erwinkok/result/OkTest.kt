// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class OkTest {
    @Test
    fun `oks should be equal for same value string`() {
        assertEquals(Ok("hello"), Ok("hello"))
    }

    @Test
    fun `oks should be equal for same value float`() {
        assertEquals(Ok(123.45), Ok(123.45))
    }

    @Test
    fun `oks should be equal for same ok`() {
        val ok = Ok(true)
        assertSame(ok, ok)
    }

    @Test
    fun `oks should not be equal for different value`() {
        assertNotEquals(Ok("hello"), Ok("world"))
    }

    @Test
    fun `oks should be equal for same ok object`() {
        val ok = Ok(12)
        assertTrue(ok == ok)
    }

    @Test
    fun `oks should not be equal for error and different object`() {
        assertFalse(Ok("hello").equals(123))
    }

    @Test
    fun `has same hashCode for same object`() {
        val ok = Ok(5678.9)
        assertEquals(ok.hashCode(), ok.hashCode())
    }

    @Test
    fun `has same hashCode for same ok`() {
        assertEquals(Ok("hello").hashCode(), Ok("hello").hashCode())
    }

    @Test
    fun `has different hashCode for different ok`() {
        assertNotEquals(Ok(1234).hashCode(), Ok("example").hashCode())
    }

    @Test
    fun `returns error string for toString`() {
        assertEquals("Ok(hello world)", Ok("hello world").toString())
    }
}
