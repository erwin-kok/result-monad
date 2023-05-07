// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
@file:OptIn(ExperimentalCoroutinesApi::class)

package org.erwinkok.result

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class CombinedResultTest {
    @Test
    fun `initially no errors`() {
        val combinedResult = CombinedResult<Unit>()
        assertFalse(combinedResult.hasErrors)
    }

    @Test
    fun `has errors when error added`() {
        val combinedResult = CombinedResult<Unit>()
        combinedResult.recordResult { Err("whatever") }
        assertTrue(combinedResult.hasErrors)
    }

    @Test
    fun `combines two errors`() {
        val combinedResult = CombinedResult<Unit>()
        combinedResult.recordResult { Err("Error 1 message") }
        combinedResult.recordResult { Err("Error 2 message") }
        assertTrue(combinedResult.hasErrors)
        assertErrorResult(
            "The following errors occurred: \n" +
                "  * Error 1 message\n" +
                "  * Error 2 message"
        ) {
            combinedResult.result()
        }
    }

    @Test
    fun `skips errors`() {
        val combinedResult = CombinedResult<Unit>(true, 3)
        combinedResult.recordResult { Err("Error 1 message") }
        combinedResult.recordResult { Err("Error 2 message") }
        combinedResult.recordResult { Err("Error 3 message") }
        combinedResult.recordResult { Err("Error 4 message") }
        assertTrue(combinedResult.hasErrors)
        assertErrorResult(
            "The following errors occurred: \n" +
                "  * Error 1 message\n" +
                "  * Error 2 message\n" +
                "  * Error 3 message\n" +
                "    ... skipping 1 errors ..."
        ) {
            combinedResult.result()
        }
    }

    @Test
    fun `collects results`() {
        val combinedResult = CombinedResult<Int>()
        combinedResult.recordResult { Ok(123) }
        combinedResult.recordResult { Ok(456) }
        combinedResult.recordResult { Ok(789) }
        assertFalse(combinedResult.hasErrors)
        assertEquals(Ok(listOf(123, 456, 789)), combinedResult.result())
    }

    @Test
    fun `collects results and error`() {
        val combinedResult = CombinedResult<Int>()
        combinedResult.recordResult { Ok(123) }
        combinedResult.recordResult { Err("AnError") }
        combinedResult.recordResult { Ok(789) }
        assertTrue(combinedResult.hasErrors)
        assertErrorResult(
            "The following errors occurred: \n" +
                "  * AnError"
        ) {
            combinedResult.result()
        }
    }

    @Test
    fun `collects results and error co`() = runTest {
        val combinedResult = CombinedResult<Int>()
        combinedResult.recordResultCo { Ok(123) }
        combinedResult.recordResultCo { Err("AnError") }
        combinedResult.recordResultCo { Ok(789) }
        assertTrue(combinedResult.hasErrors)
        assertErrorResult(
            "The following errors occurred: \n" +
                "  * AnError"
        ) {
            combinedResult.result()
        }
    }
}
