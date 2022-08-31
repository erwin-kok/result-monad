package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ErrorTest {
    @Test
    fun `errors should be equal for same string`() {
        assertEquals(Error("hello"), Error("hello"))
    }

    @Test
    fun `errors should be equal for same error`() {
        val error = Error("hello")
        assertSame(error, error)
    }

    @Test
    fun `errors should be equal for string and throwable`() {
        assertEquals(Error("illegal"), Error(IllegalArgumentException("illegal")))
    }

    @Test
    fun `errors should be unknown for string and empty throwable`() {
        assertEquals(Error("<unknown>"), Error(IllegalArgumentException()))
    }

    @Test
    fun `errors should not be equal for different string`() {
        assertNotEquals(Error("hello"), Error("world"))
    }

    @Test
    fun `errors should be equal for same error object`() {
        val error = Error("hello")
        assertTrue(error == error)
    }

    @Test
    fun `errors should not be equal for error and different object`() {
        assertFalse(Error("hello").equals(123))
    }

    @Test
    fun `has same hashCode for same object`() {
        val error = Error("hello")
        assertEquals(error.hashCode(), error.hashCode())
    }

    @Test
    fun `has same hashCode for same error`() {
        assertEquals(Error("hello").hashCode(), Error("hello").hashCode())
    }

    @Test
    fun `has different hashCode for different error`() {
        assertNotEquals(Error("hello").hashCode(), Error("world").hashCode())
    }

    @Test
    fun `returns error string for toString`() {
        assertEquals("hello world", Error("hello world").toString())
    }

    @Test
    fun `errorMessage matches for throwable`() {
        assertEquals("hello world", errorMessage(IllegalArgumentException("hello world")))
    }

    @Test
    fun `errorMessage is unknown for empty throwable`() {
        assertEquals("<unknown>", errorMessage(IllegalArgumentException()))
    }
}
