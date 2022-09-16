@file:OptIn(ExperimentalContracts::class)

package org.erwinkok.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class Result<out V> {
    companion object {
        fun <A, B, V> zip(f1: () -> Result<A>, f2: () -> Result<B>, combine: (A, B) -> Result<V>): Result<V> {
            val v1 = f1().getOrElse { return Err(it) }
            val v2 = f2().getOrElse { return Err(it) }
            return combine(v1, v2)
        }

        fun <A, B, C, V> zip(f1: () -> Result<A>, f2: () -> Result<B>, f3: () -> Result<C>, combine: (A, B, C) -> Result<V>): Result<V> {
            val v1 = f1().getOrElse { return Err(it) }
            val v2 = f2().getOrElse { return Err(it) }
            val v3 = f3().getOrElse { return Err(it) }
            return combine(v1, v2, v3)
        }

        fun <A, B, C, D, V> zip(f1: () -> Result<A>, f2: () -> Result<B>, f3: () -> Result<C>, f4: () -> Result<D>, combine: (A, B, C, D) -> Result<V>): Result<V> {
            val v1 = f1().getOrElse { return Err(it) }
            val v2 = f2().getOrElse { return Err(it) }
            val v3 = f3().getOrElse { return Err(it) }
            val v4 = f4().getOrElse { return Err(it) }
            return combine(v1, v2, v3, v4)
        }

        fun <A, B, C, D, E, V> zip(f1: () -> Result<A>, f2: () -> Result<B>, f3: () -> Result<C>, f4: () -> Result<D>, f5: () -> Result<E>, combine: (A, B, C, D, E) -> Result<V>): Result<V> {
            val v1 = f1().getOrElse { return Err(it) }
            val v2 = f2().getOrElse { return Err(it) }
            val v3 = f3().getOrElse { return Err(it) }
            val v4 = f4().getOrElse { return Err(it) }
            val v5 = f5().getOrElse { return Err(it) }
            return combine(v1, v2, v3, v4, v5)
        }
    }
}

inline infix fun <V> Result<V>.onSuccess(action: (V) -> Unit): Result<V> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is Ok) {
        action(value)
    }
    return this
}

inline infix fun <V> Result<V>.onFailure(action: (Error) -> Unit): Result<V> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is Err) {
        action(error)
    }
    return this
}

fun <V> Result<V>.get(): V? {
    contract {
        returnsNotNull() implies (this@get is Ok<V>)
        returns(null) implies (this@get is Err)
    }

    return when (this) {
        is Ok -> value
        is Err -> null
    }
}

fun <V> Result<V>.getError(): Error? {
    contract {
        returns(null) implies (this@getError is Ok<V>)
        returnsNotNull() implies (this@getError is Err)
    }

    return when (this) {
        is Ok -> null
        is Err -> error
    }
}

infix fun <V> Result<V>.getOr(default: V): V {
    return when (this) {
        is Ok -> value
        is Err -> default
    }
}

infix fun <V> Result<V>.getErrorOr(default: Error): Error {
    return when (this) {
        is Ok -> default
        is Err -> error
    }
}

inline infix fun <V> Result<V>.getOrElse(transform: (Error) -> V): V {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }

    return when (this) {
        is Ok -> value
        is Err -> transform(error)
    }
}

inline infix fun <V> Result<V>.getErrorOrElse(transform: (V) -> Error): Error {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }

    return when (this) {
        is Ok -> transform(value)
        is Err -> error
    }
}

fun <V> Result<V>.getOrThrow(): V {
    contract {
        returns() implies (this@getOrThrow is Ok<V>)
    }

    return when (this) {
        is Ok -> value
        is Err -> throw error
    }
}

inline infix fun <V> Result<V>.getOrThrow(transform: (Error) -> Throwable): V {
    contract {
        returns() implies (this@getOrThrow is Ok<V>)
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }

    return when (this) {
        is Ok -> value
        is Err -> throw transform(error)
    }
}

inline infix fun <V, U> Result<V>.map(transform: (V) -> U): Result<U> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> Ok(transform(value))
        is Err -> this
    }
}

inline infix fun <V, U> Result<V>.flatMap(transform: (V) -> Result<U>): Result<U> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> transform(value)
        is Err -> this
    }
}

inline fun <V, U> Result<V>.mapOr(default: U, transform: (V) -> U): U {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> transform(value)
        is Err -> default
    }
}

inline fun <V, U> Result<V>.mapOrElse(default: (Error) -> U, transform: (V) -> U): U {
    contract {
        callsInPlace(default, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> transform(value)
        is Err -> default(error)
    }
}

inline fun <V, U> Result<V>.mapBoth(success: (V) -> U, failure: (Error) -> U): U {
    contract {
        callsInPlace(success, InvocationKind.AT_MOST_ONCE)
        callsInPlace(failure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> success(value)
        is Err -> failure(error)
    }
}

inline fun <V, U> Result<V>.mapEither(success: (V) -> U, failure: (Error) -> Error): Result<U> {
    contract {
        callsInPlace(success, InvocationKind.AT_MOST_ONCE)
        callsInPlace(failure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> Ok(success(value))
        is Err -> Err(failure(error))
    }
}

inline fun <V> Result<V>.toErrorIf(predicate: (V) -> Boolean, transform: (V) -> Error): Result<V> {
    contract {
        callsInPlace(predicate, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> if (predicate(value)) {
            Err(transform(value))
        } else {
            this
        }

        is Err -> this
    }
}

inline fun <V> Result<V>.toErrorUnless(predicate: (V) -> Boolean, transform: (V) -> Error): Result<V> {
    contract {
        callsInPlace(predicate, InvocationKind.AT_MOST_ONCE)
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Ok -> if (!predicate(value)) {
            Err(transform(value))
        } else {
            this
        }

        is Err -> this
    }
}

fun <V> combine(vararg results: Result<V>): Result<List<V>> {
    return results.asIterable().combine()
}

fun <V> Iterable<Result<V>>.combine(): Result<List<V>> {
    return Ok(
        map {
            when (it) {
                is Ok -> it.value
                is Err -> return it
            }
        }
    )
}
