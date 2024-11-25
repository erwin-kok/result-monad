// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
        assertEquals(Error("illegal <java.lang.IllegalArgumentException>"), Error(IllegalArgumentException("illegal")))
    }

    @Test
    fun `errors should be unknown for string and empty throwable`() {
        assertEquals(Error("(unknown throwable message) <java.lang.IllegalArgumentException>"), Error(IllegalArgumentException()))
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
        assertEquals("(unknown throwable message)", errorMessage(IllegalArgumentException()))
    }

    @Test
    fun `has stackTrace for Error`() {
        val stackTrace = Error("Oh No!").stackTrace()
        val methodName = object {}.javaClass.enclosingMethod.name
        assertTrue(stackTrace.contains("Error occurred: Oh No!"))
        assertTrue(stackTrace.contains(methodName))
        assertFalse(stackTrace.contains("Caused by:"))
    }

    @Test
    fun `unspecified Error`() {
        assertEquals("(unspecified error)", errorMessage(Error()))
    }

    @Test
    fun `has stackTrace with caused by for thrown exception`() {
        val exception = assertThrows<IllegalArgumentException> { throw IllegalArgumentException("Test") }
        val stackTrace = Error(exception).stackTrace()
        val methodName = object {}.javaClass.enclosingMethod.name
        assertTrue(stackTrace.contains("Error occurred: Test <java.lang.IllegalArgumentException>"))
        assertTrue(stackTrace.contains(methodName))
        assertTrue(stackTrace.contains("Caused by:"))
        assertTrue(stackTrace.contains("java.lang.IllegalArgumentException: Test"))
    }

    @Test
    fun `has stackTrace for copied Error`() {
        val error = Error("Oh Yes!")
        val stackTrace = Error(error).stackTrace()
        val methodName = object {}.javaClass.enclosingMethod.name
        assertTrue(stackTrace.contains("Error occurred: Oh Yes!"))
        assertTrue(stackTrace.contains(methodName))
        assertFalse(stackTrace.contains("Caused by:"))
    }

    @Test
    fun `can extend Error`() {
        val error: Error = ExtendedError("An extended error", "some info")
        assertErrorResult("Something went wrong: An extended error (some info)") { Err(error) }
        val extendedError = error as? ExtendedError
        assertNotNull(extendedError)
        assertEquals("some info", extendedError?.extended)
    }

    class ExtendedError(val msg: String, val extended: String) : Error() {
        override val message: String
            get() = "Something went wrong: $msg ($extended)"
    }
}
