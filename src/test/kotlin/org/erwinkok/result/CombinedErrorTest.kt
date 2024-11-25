// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
package org.erwinkok.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class CombinedErrorTest {
    @Test
    fun `initially no errors`() {
        val combinedError = CombinedError()
        assertFalse(combinedError.hasErrors)
    }

    @Test
    fun `has errors when error added`() {
        val combinedError = CombinedError()
        combinedError.recordError(Error("Whatever"))
        assertTrue(combinedError.hasErrors)
    }

    @Test
    fun `combines two errors`() {
        val combinedError = CombinedError()
        combinedError.recordError(Error("Error 1 message"))
        combinedError.recordError { "Error 2 message" }
        assertTrue(combinedError.hasErrors)
        assertEquals(
            "The following errors occurred: \n" +
                "  * Error 1 message\n" +
                "  * Error 2 message",
            combinedError.error().message,
        )
    }

    @Test
    fun `skips errors`() {
        val combinedError = CombinedError(maxErrors = 3)
        combinedError.recordError(Error("Error 1 message"))
        combinedError.recordError(Error("Error 2 message"))
        combinedError.recordError { "Error 3 message" }
        combinedError.recordError { "Error 4 message" }
        assertTrue(combinedError.hasErrors)
        assertEquals(
            "The following errors occurred: \n" +
                "  * Error 1 message\n" +
                "  * Error 2 message\n" +
                "  * Error 3 message\n" +
                "    ... skipping 1 errors ...",
            combinedError.error().message,
        )
    }

    @Test
    fun `custom error message`() {
        val combinedError = CombinedError(maxErrors = 3)
        combinedError.recordError(Error("Error 1 message"))
        combinedError.recordError(Error("Error 2 message"))
        combinedError.recordError { "Error 3 message" }
        combinedError.recordError { "Error 4 message" }
        assertTrue(combinedError.hasErrors)
        assertEquals(
            "While doing the dishes, the following things happened: \n" +
                "  * Error 1 message\n" +
                "  * Error 2 message\n" +
                "  * Error 3 message\n" +
                "    ... skipping 1 errors ...",
            combinedError.error("While doing the dishes, the following things happened: ").message,
        )
    }
}
