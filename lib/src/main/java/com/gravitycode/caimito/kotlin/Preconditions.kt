package com.gravitycode.solitaryfitnessapp.util.error

import android.util.Log
import com.gravitycode.solitaryfitnessapp.util.AppConfiguration
import org.jetbrains.annotations.ApiStatus.Experimental

private const val TAG = "DEBUG_ERROR"

private class Thrower(private val throwable: (String, Throwable?) -> Throwable) {

    operator fun invoke(message: String, cause: Throwable?): Nothing {
        throw throwable(message, cause)
    }
}

private val ILLEGAL_ARGUMENT_EXCEPTION = Thrower { message: String, cause: Throwable? ->
    IllegalArgumentException(message, cause)
}

private val ILLEGAL_STATE_EXCEPTION = Thrower { message: String, cause: Throwable? ->
    IllegalStateException(message, cause)
}

// /**
//  * Checks if [condition] is `true`, otherwise handles the error based on [AppConfiguration].
//  *
//  * If [condition] is `true` executes [block] if available, otherwise does nothing.
//  *
//  * ### If [condition] is `false`
//  * If [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError]
//  * return `true`, throw an [IllegalArgumentException] containing [message].
//  *
//  * If only [AppConfiguration.isDebug] returns `true`, log [message] at log level [android.util.Log.ERROR].
//  *
//  * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `false` nothing happens.
//  *
//  * @param condition The condition to check for
//  * @param message The message describing the failed condition if it occurs
//  * @param block The function to execute if the condition passes
//  * */
@Experimental
fun require(condition: Boolean, message: String, block: ((Boolean) -> Unit)? = null) {
    if (condition) {
        block?.invoke(true)
    } else {
        error(message, null, ILLEGAL_ARGUMENT_EXCEPTION) { _ -> block?.invoke(false) }
    }
}

/**
 * Throw an error or log an exception based on [AppConfiguration].
 *
 * If [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `true`,
 * throw an [IllegalStateException] containing [message] and [cause].
 *
 * If only [AppConfiguration.isDebug] returns `true`, log [message] and [cause] at log level [android.util.Log.ERROR].
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `false` nothing happens
 *
 * @param message The message describing the error
 * @param cause The cause of the error if one exists
 * */
fun error(message: String, cause: Throwable? = null) {
    error(message, cause, ILLEGAL_STATE_EXCEPTION) { _ -> }
}

/**
 * Throw an error, or log an exception, and/or execute a recovery function based on [AppConfiguration].
 *
 * If [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `true`,
 * throw an [IllegalStateException] containing [message] and [cause].
 *
 * If only [AppConfiguration.isDebug] returns `true`, log [message] and [cause] at log
 * level [android.util.Log.ERROR] and execute [recover], returning its return value.
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError]
 * return `false`, execute [recover] and returns its return value.
 *
 * @param message The message describing the error
 * @param cause The cause of the error if one exists
 * @param recover The recovery function, which takes [message] as its parameter
 * */
fun <T> error(message: String, cause: Throwable? = null, recover: (String) -> T): T {
    return error(message, cause, ILLEGAL_STATE_EXCEPTION, recover)
}

/**
 * Throw an error or log an exception based on [AppConfiguration].
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `true`, throw an
 * [IllegalStateException] with the specified [message] and cause retrieved from [Result.exceptionOrNull].
 *
 * If only [AppConfiguration.isDebug] returns `true`, log [message] and the cause
 * retrieved from [Result.exceptionOrNull] at level log [android.util.Log.ERROR].
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `false` nothing happens.
 *
 * @param message The message describing the error
 * @param result The result which [Result.exceptionOrNull] will be called on to retrieve the cause if one exists
 * */
fun error(message: String, result: Result<*>) {
    error(message, result.exceptionOrNull(), ILLEGAL_STATE_EXCEPTION) { _ -> }
}

/**
 * Throw an error, or log an exception, and/or execute a recovery function based on [AppConfiguration].
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `true`, throw
 * an [IllegalStateException] containing [message] and the cause retrieved from [Result.exceptionOrNull].
 *
 * If only [AppConfiguration.isDebug] returns `true`, log [message] and the result of [Result.exceptionOrNull]
 * at log level [android.util.Log.ERROR] and executes [recover] returning its return value.
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError]
 * return `false`, execute [recover] and returns its return value.
 *
 * @param message The message describing the error
 * @param result The result which [Result.exceptionOrNull] will be called on to retrieve the cause if one exists
 * @param recover The recovery function, which takes [message] as its parameter
 * */
fun <T> error(
    message: String,
    result: Result<*>,
    recover: (String) -> T
): T {
    return error(message, result.exceptionOrNull(), ILLEGAL_STATE_EXCEPTION, recover)
}

/**
 * Throw an error, or log an exception, and/or execute a recovery function based on [AppConfiguration].
 *
 * If [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError] return `true`,
 * invoke [thrower] which will throw an exception containing [message] and [cause].
 *
 * If only [AppConfiguration.isDebug] returns `true`, log [message] and [cause] at log
 * level [android.util.Log.ERROR] and execute [recover], returning its return value.
 *
 * If both [AppConfiguration.isDebug] and [AppConfiguration.shouldCrashOnError]
 * return `false`, execute [recover] and returns its return value.
 *
 * @param message The message describing the error
 * @param cause The cause of the error if one exists
 * @param thrower The [Thrower] which will be invoked to throw an exception if required.
 * @param recover The recovery function, which takes [message] as its parameter
 * */
private fun <T> error(
    message: String,
    cause: Throwable? = null,
    thrower: Thrower,
    recover: (String) -> T
): T {
    return if (AppConfiguration.shouldCrashOnError()) {
        thrower(message, cause)
    } else if (AppConfiguration.isDebug()) {
        Log.e(TAG, message, cause)
        recover(message)
    } else {
        recover(message)
    }
}