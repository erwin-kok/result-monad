package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ErrorsKtTest {
    @Test
    fun `assertErrorResult asserts for err`() {
        assertErrorResult("Hello") { Err("Hello") }
    }

    @Test
    fun `assertErrorResult throws exception for ok`() {
        assertEquals("Expected error, but was: Ok -- No Error", assertThrows<IllegalStateException> { assertErrorResult("Don't care") { Ok("Ok -- No Error") } }.message)
    }

    @Test
    fun `assertErrorResult asserts for err throws exception when not matching expected error`() {
        assertEquals("Error message 'Illegal Argument' was not equal to 'Number Format'", assertThrows<IllegalStateException> { assertErrorResult("Number Format") { Err("Illegal Argument") } }.message)
    }

    @Test
    fun `assertErrorResultMatches asserts when matching err`() {
        assertErrorResultMatches(Regex("[a-z]*")) { Err("hello") }
    }

    @Test
    fun `assertErrorResultMatches throws exception for ok`() {
        assertEquals("Expected error, but was: Ok -- No Error", assertThrows<IllegalStateException> { assertErrorResultMatches(Regex("[a-z]*")) { Ok("Ok -- No Error") } }.message)
    }

    @Test
    fun `assertErrorResultMatches asserts when not matching err`() {
        assertEquals("Error message '12345' did not match '[a-z]*'", assertThrows<IllegalStateException> { assertErrorResultMatches(Regex("[a-z]*")) { Err("12345") } }.message)
    }

    @Test
    fun `returns value when expectNoErrors for ok`() {
        assertEquals("hello", Ok("hello").expectNoErrors())
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `returns error when expectNoErrors for err`() {
        assertEquals("No Errors expected, but got: illegal", assertThrows<IllegalStateException> { Err("illegal").expectNoErrors() }.message)
    }
}
