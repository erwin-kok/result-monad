package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ErrTest {
    @Test
    fun `errors should be equal for same string`() {
        assertEquals(Err("hello"), Err("hello"))
    }

    @Test
    fun `errors should be equal for same error`() {
        val error = Err("hello")
        assertSame(error, error)
    }

    @Test
    fun `errors should be equal for error and string`() {
        assertEquals(Err(Error("hello")), Err("hello"))
    }

    @Test
    fun `errors should not be equal for different string`() {
        assertNotEquals(Err("hello"), Err("world"))
    }

    @Test
    fun `errors should not be equal for different error`() {
        assertNotEquals(Err(Error("hello")), Err(Error("world")))
    }

    @Test
    fun `errors should not be equal for different error and string`() {
        assertNotEquals(Err(Error("hello")), Err("world"))
    }

    @Test
    fun `errors should be equal for same error object`() {
        val err = Err("hello")
        assertTrue(err == err)
    }

    @Test
    fun `errors should not be equal for error and different object`() {
        assertFalse(Err("hello").equals(123))
    }

    @Test
    fun `has same hashCode for same object`() {
        val err = Err("hello")
        assertEquals(err.hashCode(), err.hashCode())
    }

    @Test
    fun `has same hashCode for same error`() {
        assertEquals(Err("hello").hashCode(), Err("hello").hashCode())
    }

    @Test
    fun `has different hashCode for different error`() {
        assertNotEquals(Err("hello").hashCode(), Err("world").hashCode())
    }

    @Test
    fun `returns error string for toString`() {
        assertEquals("Err(hello world)", Err("hello world").toString())
    }
}
