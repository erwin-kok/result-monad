package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ResultTest {
    private class CustomException(message: String) : Throwable(message)
    private class Spy(var hit: Boolean = false)

    private val constantValue = 456

    @Test
    fun `invokes action when onSuccess for ok`() {
        val spy = Spy()
        Ok(spy).onSuccess { it.hit = true }
        assertTrue(spy.hit)
    }

    @Test
    fun `does not invoke action when onSuccess for err`() {
        val spy = Spy()
        Err("don't care").onSuccess { spy.hit = true }
        assertFalse(spy.hit)
    }

    @Test
    fun `does not invoke action when onFailure for ok`() {
        val spy = Spy()
        Ok(spy).onFailure { spy.hit = true }
        assertFalse(spy.hit)
    }

    @Test
    fun `invokes action when onFailure for err`() {
        val spy = Spy()
        Err("don't care").onFailure { spy.hit = true }
        assertTrue(spy.hit)
    }

    @Test
    fun `returns ok when get for ok`() {
        assertEquals(12, Ok(12).get())
    }

    @Test
    @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
    fun `returns null when get for err`() {
        assertNull(Err("error").get())
    }

    @Test
    fun `returns null when getError for ok`() {
        assertNull(Ok("example").getError())
    }

    @Test
    fun `returns err when getError for err`() {
        assertEquals(Error("example"), Err("example").getError())
    }

    @Test
    fun `returns ok when getOr for ok`() {
        assertEquals("hello", Ok("hello").getOr("world"))
    }

    @Test
    fun `returns default when getOr for err`() {
        assertEquals("default", Err("error").getOr("default"))
    }

    @Test
    fun `returns ok for getOrThrow for ok`() {
        assertEquals("hello", Ok("hello").getOrThrow())
    }

    @Test
    fun `returns err for getOrThrow for err`() {
        assertEquals(Error("something wrong"), assertThrows<Error> { Err("something wrong").getOrThrow() })
    }

    @Test
    fun `returns ok for getOrThrow for ok with custom error`() {
        assertEquals("hello", Ok("hello").getOrThrow { CustomException("Failed") })
    }

    @Test
    fun `returns err for getOrThrow for err with custom error`() {
        assertEquals("CustomException: something wrong", assertThrows<CustomException> { Err("something wrong").getOrThrow { error -> CustomException("CustomException: ${error.message}") } }.message)
    }

    @Test
    fun `returns default for getErrorOr for ok`() {
        assertEquals(Error("world"), Ok("hello").getErrorOr(Error("world")))
    }

    @Test
    fun `returns err for getErrorOr for err`() {
        assertEquals(Error("hello"), Err("hello").getErrorOr(Error("world")))
    }

    @Test
    fun `returns ok for getOrElse for ok`() {
        assertEquals("hello", Ok("hello").getOrElse { "world" })
    }

    @Test
    fun `returns default for getOrElse for err`() {
        assertEquals("world", Err("hello").getOrElse { "world" })
    }

    @Test
    fun `returns default for getErrorOrElse for ok`() {
        assertEquals(Error("world"), Ok("hello").getErrorOrElse { Error("world") })
    }

    @Test
    fun `returns err for getErrorOrElse for err`() {
        assertEquals(Error("hello"), Err("hello").getErrorOrElse { Error("world") })
    }

    @Test
    fun `return transformed value when map for ok`() {
        assertEquals(123, Ok(61).map { it + 62 }.get())
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return err when map for err`() {
        val helloError = Error("hello")
        val result = Err(helloError).map { "hello $it" } as Err
        assertSame(helloError, result.error)
    }

    @Test
    fun `return transformed value when flatMap for ok`() {
        assertEquals(123, Ok(61).flatMap { Ok(it + 62) }.get())
    }

    @Test
    fun `return error when flatMap for err`() {
        assertEquals(Error("world"), Ok(61).flatMap { Err("world") }.getError())
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return first error when flatMap for err and ok`() {
        val helloError = Error("hello")
        val result = Err(helloError).flatMap { Ok("DontCare") } as Err
        assertSame(helloError, result.error)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return first error when flatMap for err and err`() {
        val helloError = Error("hello")
        val result = Err(helloError).flatMap { Err("world") } as Err
        assertSame(helloError, result.error)
    }

    @Test
    fun `returns value when mapOr for ok`() {
        val value = Ok("foo").mapOr(42, String::length)
        assertEquals(3, value)
    }

    @Test
    fun `returns default when mapOr for err`() {
        val value = Err("bar").mapOr(42, String::length)
        assertEquals(42, value)
    }

    @Test
    fun `return transformed value when mapOrElse for ok`() {
        val value = Ok("foo").mapOrElse({ constantValue * 2 }, String::length)
        assertEquals(3, value)
    }

    @Test
    fun `return transformed value when mapOrElse for err`() {
        val value = Err("bar").mapOrElse({ constantValue * 2 }, String::length)
        assertEquals(912, value)
    }

    @Test
    fun `return transformed value when mapBoth for ok`() {
        val value = Ok("hello").mapBoth(
            success = { "$it world" },
            failure = { "$it universe" }
        )
        assertEquals("hello world", value)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return transformed value when mapBoth for err`() {
        val error = Err("hello").mapBoth(
            success = { "$it world" },
            failure = { "$it universe" }
        )
        assertEquals("hello universe", error)
    }

    @Test
    fun `return transformed value when mapEither for ok`() {
        val result = Ok(100).mapEither(
            success = { it + 200 },
            failure = { Error("$it") }
        ) as Ok
        assertEquals(300, result.value)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return transformed value when mapEither for err`() {
        val result = Err("hello").mapEither(
            success = { "$it world" },
            failure = { Error("$it universe") }
        ) as Err
        assertEquals(Error("hello universe"), result.error)
    }

    @Test
    fun `return error when toErrorIf for ok and predicate is true`() {
        val result = Ok("hello").toErrorIf(
            predicate = { true },
            transform = { Error("$it world") }
        ) as Err
        assertEquals(Error("hello world"), result.error)
    }

    @Test
    fun `return value when toErrorIf for ok and predicate is false`() {
        val result = Ok("hello").toErrorIf(
            predicate = { false },
            transform = { Error("$it world") }
        ) as Ok
        assertEquals("hello", result.value)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return first err when toErrorIf for err and predicate is true`() {
        val result = Err("first").toErrorIf(
            predicate = { true },
            transform = { Error("$it world") }
        ) as Err
        assertEquals(Error("first"), result.error)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return first err when toErrorIf for err and predicate is false`() {
        val result = Err("first").toErrorIf(
            predicate = { false },
            transform = { Error("$it world") }
        ) as Err
        assertEquals(Error("first"), result.error)
    }

    @Test
    fun `return error when toErrorUnless for ok and predicate is false`() {
        val result = Ok("hello").toErrorUnless(
            predicate = { false },
            transform = { Error("$it world") }
        ) as Err
        assertEquals(Error("hello world"), result.error)
    }

    @Test
    fun `return value when toErrorUnless for ok and predicate is true`() {
        val result = Ok("hello").toErrorUnless(
            predicate = { true },
            transform = { Error("$it world") }
        ) as Ok
        assertEquals("hello", result.value)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return first err when toErrorUnless for err and predicate is true`() {
        val result = Err("first").toErrorUnless(
            predicate = { true },
            transform = { Error("$it world") }
        ) as Err
        assertEquals(Error("first"), result.error)
    }

    @Test
    @Suppress("UNREACHABLE_CODE")
    fun `return first err when toErrorUnless for err and predicate is false`() {
        val result = Err("first").toErrorUnless(
            predicate = { false },
            transform = { Error("$it world") }
        ) as Err
        assertEquals(Error("first"), result.error)
    }

    @Test
    fun `returns all values when ok`() {
        val values = combine(
            Ok(10),
            Ok(20),
            Ok(30)
        ).get()!!
        assertEquals(3, values.size)
        assertEquals(10, values[0])
        assertEquals(20, values[1])
        assertEquals(30, values[2])
    }

    @Test
    fun `return first error when err`() {
        val firstError = Error("First")
        val secondError = Error("second")
        val result = combine(
            Ok(20),
            Ok(40),
            Err(firstError),
            Ok(60),
            Err(secondError),
            Ok(80)
        ) as Err
        assertSame(firstError, result.error)
    }

    @Test
    fun `zip of two succeeding actions`() {
        val value = Result.zip(
            { sum(1, 3) },
            { Ok("Hello ") },
            { a, b -> Ok(b + a) }
        ).expectNoErrors()
        assertEquals("Hello 4", value)
    }

    @Test
    fun `zip of one succeeding action and an error`() {
        assertErrorResult("This is an Error") {
            Result.zip(
                { sum(1, 3) },
                { Err("This is an Error") },
                { _, _ -> Ok("Not reached") }
            )
        }
    }

    @Test
    fun `zip of two succeeding actions and an error result`() {
        assertErrorResult("An Error!") {
            Result.zip(
                { sum(1, 3) },
                { sum(2, 4) },
                { _, _ -> Err("An Error!") }
            )
        }
    }

    @Test
    fun `zip of three succeeding actions`() {
        val value = Result.zip(
            { sum(1, 3) },
            { Ok("Hello") },
            { Ok(1.234) },
            { a, b, c -> Ok("$b $a $c") }
        ).expectNoErrors()
        assertEquals("Hello 4 1.234", value)
    }

    @Test
    fun `zip of three actions with error`() {
        assertErrorResult("Oh No!") {
            Result.zip(
                { sum(1, 3) },
                { Ok("Hello") },
                { Err("Oh No!") },
                { _, _, _ -> Ok("Not reached") }
            )
        }
    }

    @Test
    fun `zip of four succeeding actions`() {
        val value = Result.zip(
            { sum(1, 3) },
            { Ok("Hello") },
            { Ok(1.234) },
            { Ok("World") },
            { a, b, c, d -> Ok("$b $a $c $d") }
        ).expectNoErrors()
        assertEquals("Hello 4 1.234 World", value)
    }

    @Test
    fun `zip of four actions with error`() {
        assertErrorResult("Oh No!") {
            Result.zip(
                { Err("Oh No!") },
                { sum(1, 3) },
                { Ok(1.234) },
                { Ok("Hello") },
                { _, _, _, _ -> Ok("Not reached") }
            )
        }
    }

    @Test
    fun `zip of five succeeding actions`() {
        val value = Result.zip(
            { sum(1, 3) },
            { Ok("Hello") },
            { Ok(1.234) },
            { sum(5, 7) },
            { Ok("World") },
            { a, b, c, d, e -> Ok("$b $e $a $c $d") }
        ).expectNoErrors()
        assertEquals("Hello World 4 1.234 12", value)
    }

    @Test
    fun `zip of five actions with error`() {
        assertErrorResult("An Error again!") {
            Result.zip(
                { sum(1, 3) },
                { Ok(1.234) },
                { Ok("Hello") },
                { Err("An Error again!") },
                { Ok("World") },
                { _, _, _, _, _ -> Ok("Not reached") }
            )
        }
    }

    private fun sum(a: Int, b: Int): Result<Int> {
        return Ok(a + b)
    }
}
