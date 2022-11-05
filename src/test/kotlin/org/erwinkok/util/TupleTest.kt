package org.erwinkok.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Random

internal class TupleTest {
    @Test
    fun `tuple 1`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val t = Tuple1(r1)
            assertEquals(r1, t._1)
            val (v) = t
            assertEquals(r1, v)

            val r2 = random.nextInt()
            val a = Tuple(r2)
            assertEquals(r2, a._1)
            val (va) = a
            assertEquals(r2, va)
        }
    }

    @Test
    fun `tuple 2`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val t = Tuple2(r1, r2)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            val (v1, v2) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val a = Tuple(r21, r22)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            val (va1, va2) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
        }
    }

    @Test
    fun `tuple 3`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val t = Tuple3(r1, r2, r3)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            val (v1, v2, v3) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val a = Tuple(r21, r22, r23)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            val (va1, va2, va3) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
        }
    }

    @Test
    fun `tuple 4`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val r4 = random.nextInt()
            val t = Tuple4(r1, r2, r3, r4)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            assertEquals(r4, t._4)
            val (v1, v2, v3, v4) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)
            assertEquals(r4, v4)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val r24 = random.nextInt()
            val a = Tuple(r21, r22, r23, r24)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            assertEquals(r24, a._4)
            val (va1, va2, va3, va4) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
            assertEquals(r24, va4)
        }
    }

    @Test
    fun `tuple 5`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val r4 = random.nextInt()
            val r5 = random.nextInt()
            val t = Tuple5(r1, r2, r3, r4, r5)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            assertEquals(r4, t._4)
            assertEquals(r5, t._5)
            val (v1, v2, v3, v4, v5) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)
            assertEquals(r4, v4)
            assertEquals(r5, v5)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val r24 = random.nextInt()
            val r25 = random.nextInt()
            val a = Tuple(r21, r22, r23, r24, r25)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            assertEquals(r24, a._4)
            assertEquals(r25, a._5)
            val (va1, va2, va3, va4, va5) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
            assertEquals(r24, va4)
            assertEquals(r25, va5)
        }
    }

    @Test
    fun `tuple 6`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val r4 = random.nextInt()
            val r5 = random.nextInt()
            val r6 = random.nextInt()
            val t = Tuple6(r1, r2, r3, r4, r5, r6)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            assertEquals(r4, t._4)
            assertEquals(r5, t._5)
            assertEquals(r6, t._6)
            val (v1, v2, v3, v4, v5, v6) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)
            assertEquals(r4, v4)
            assertEquals(r5, v5)
            assertEquals(r6, v6)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val r24 = random.nextInt()
            val r25 = random.nextInt()
            val r26 = random.nextInt()
            val a = Tuple(r21, r22, r23, r24, r25, r26)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            assertEquals(r24, a._4)
            assertEquals(r25, a._5)
            assertEquals(r26, a._6)
            val (va1, va2, va3, va4, va5, va6) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
            assertEquals(r24, va4)
            assertEquals(r25, va5)
            assertEquals(r26, va6)
        }
    }

    @Test
    fun `tuple 7`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val r4 = random.nextInt()
            val r5 = random.nextInt()
            val r6 = random.nextInt()
            val r7 = random.nextInt()
            val t = Tuple7(r1, r2, r3, r4, r5, r6, r7)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            assertEquals(r4, t._4)
            assertEquals(r5, t._5)
            assertEquals(r6, t._6)
            assertEquals(r7, t._7)
            val (v1, v2, v3, v4, v5, v6, v7) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)
            assertEquals(r4, v4)
            assertEquals(r5, v5)
            assertEquals(r6, v6)
            assertEquals(r7, v7)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val r24 = random.nextInt()
            val r25 = random.nextInt()
            val r26 = random.nextInt()
            val r27 = random.nextInt()
            val a = Tuple(r21, r22, r23, r24, r25, r26, r27)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            assertEquals(r24, a._4)
            assertEquals(r25, a._5)
            assertEquals(r26, a._6)
            assertEquals(r27, a._7)
            val (va1, va2, va3, va4, va5, va6, va7) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
            assertEquals(r24, va4)
            assertEquals(r25, va5)
            assertEquals(r26, va6)
            assertEquals(r27, va7)
        }
    }

    @Test
    fun `tuple 8`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val r4 = random.nextInt()
            val r5 = random.nextInt()
            val r6 = random.nextInt()
            val r7 = random.nextInt()
            val r8 = random.nextInt()
            val t = Tuple8(r1, r2, r3, r4, r5, r6, r7, r8)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            assertEquals(r4, t._4)
            assertEquals(r5, t._5)
            assertEquals(r6, t._6)
            assertEquals(r7, t._7)
            assertEquals(r8, t._8)
            val (v1, v2, v3, v4, v5, v6, v7, v8) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)
            assertEquals(r4, v4)
            assertEquals(r5, v5)
            assertEquals(r6, v6)
            assertEquals(r7, v7)
            assertEquals(r8, v8)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val r24 = random.nextInt()
            val r25 = random.nextInt()
            val r26 = random.nextInt()
            val r27 = random.nextInt()
            val r28 = random.nextInt()
            val a = Tuple(r21, r22, r23, r24, r25, r26, r27, r28)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            assertEquals(r24, a._4)
            assertEquals(r25, a._5)
            assertEquals(r26, a._6)
            assertEquals(r27, a._7)
            assertEquals(r28, a._8)
            val (va1, va2, va3, va4, va5, va6, va7, va8) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
            assertEquals(r24, va4)
            assertEquals(r25, va5)
            assertEquals(r26, va6)
            assertEquals(r27, va7)
            assertEquals(r28, va8)
        }
    }

    @Test
    fun `tuple 9`() {
        val random = Random()
        for (i in 0..1024) {
            val r1 = random.nextInt()
            val r2 = random.nextInt()
            val r3 = random.nextInt()
            val r4 = random.nextInt()
            val r5 = random.nextInt()
            val r6 = random.nextInt()
            val r7 = random.nextInt()
            val r8 = random.nextInt()
            val r9 = random.nextInt()
            val t = Tuple9(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            assertEquals(r1, t._1)
            assertEquals(r2, t._2)
            assertEquals(r3, t._3)
            assertEquals(r4, t._4)
            assertEquals(r5, t._5)
            assertEquals(r6, t._6)
            assertEquals(r7, t._7)
            assertEquals(r8, t._8)
            assertEquals(r9, t._9)
            val (v1, v2, v3, v4, v5, v6, v7, v8, v9) = t
            assertEquals(r1, v1)
            assertEquals(r2, v2)
            assertEquals(r3, v3)
            assertEquals(r4, v4)
            assertEquals(r5, v5)
            assertEquals(r6, v6)
            assertEquals(r7, v7)
            assertEquals(r8, v8)
            assertEquals(r9, v9)

            val r21 = random.nextInt()
            val r22 = random.nextInt()
            val r23 = random.nextInt()
            val r24 = random.nextInt()
            val r25 = random.nextInt()
            val r26 = random.nextInt()
            val r27 = random.nextInt()
            val r28 = random.nextInt()
            val r29 = random.nextInt()
            val a = Tuple(r21, r22, r23, r24, r25, r26, r27, r28, r29)
            assertEquals(r21, a._1)
            assertEquals(r22, a._2)
            assertEquals(r23, a._3)
            assertEquals(r24, a._4)
            assertEquals(r25, a._5)
            assertEquals(r26, a._6)
            assertEquals(r27, a._7)
            assertEquals(r28, a._8)
            assertEquals(r29, a._9)
            val (va1, va2, va3, va4, va5, va6, va7, va8, va9) = a
            assertEquals(r21, va1)
            assertEquals(r22, va2)
            assertEquals(r23, va3)
            assertEquals(r24, va4)
            assertEquals(r25, va5)
            assertEquals(r26, va6)
            assertEquals(r27, va7)
            assertEquals(r28, va8)
            assertEquals(r29, va9)
        }
    }
}
