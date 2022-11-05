package org.erwinkok.util

data class Tuple1<out A>(val _1: A)
data class Tuple2<out A, out B>(val _1: A, val _2: B)
data class Tuple3<out A, out B, out C>(val _1: A, val _2: B, val _3: C)
data class Tuple4<out A, out B, out C, out D>(val _1: A, val _2: B, val _3: C, val _4: D)
data class Tuple5<out A, out B, out C, out D, out E>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E)
data class Tuple6<out A, out B, out C, out D, out E, out F>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E, val _6: F)
data class Tuple7<out A, out B, out C, out D, out E, out F, out G>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E, val _6: F, val _7: G)
data class Tuple8<out A, out B, out C, out D, out E, out F, out G, out H>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E, val _6: F, val _7: G, val _8: H)
data class Tuple9<out A, out B, out C, out D, out E, out F, out G, out H, out I>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E, val _6: F, val _7: G, val _8: H, val _9: I)

object Tuple {
    operator fun <A> invoke(_1: A): Tuple1<A> = Tuple1(_1)
    operator fun <A, B> invoke(_1: A, _2: B): Tuple2<A, B> = Tuple2(_1, _2)
    operator fun <A, B, C> invoke(_1: A, _2: B, _3: C): Tuple3<A, B, C> = Tuple3(_1, _2, _3)
    operator fun <A, B, C, D> invoke(_1: A, _2: B, _3: C, _4: D): Tuple4<A, B, C, D> = Tuple4(_1, _2, _3, _4)
    operator fun <A, B, C, D, E> invoke(_1: A, _2: B, _3: C, _4: D, _5: E): Tuple5<A, B, C, D, E> = Tuple5(_1, _2, _3, _4, _5)
    operator fun <A, B, C, D, E, F> invoke(_1: A, _2: B, _3: C, _4: D, _5: E, _6: F): Tuple6<A, B, C, D, E, F> = Tuple6(_1, _2, _3, _4, _5, _6)
    operator fun <A, B, C, D, E, F, G> invoke(_1: A, _2: B, _3: C, _4: D, _5: E, _6: F, _7: G): Tuple7<A, B, C, D, E, F, G> = Tuple7(_1, _2, _3, _4, _5, _6, _7)
    operator fun <A, B, C, D, E, F, G, H> invoke(_1: A, _2: B, _3: C, _4: D, _5: E, _6: F, _7: G, _8: H): Tuple8<A, B, C, D, E, F, G, H> = Tuple8(_1, _2, _3, _4, _5, _6, _7, _8)
    operator fun <A, B, C, D, E, F, G, H, I> invoke(_1: A, _2: B, _3: C, _4: D, _5: E, _6: F, _7: G, _8: H, _9: I): Tuple9<A, B, C, D, E, F, G, H, I> = Tuple9(_1, _2, _3, _4, _5, _6, _7, _8, _9)
}
