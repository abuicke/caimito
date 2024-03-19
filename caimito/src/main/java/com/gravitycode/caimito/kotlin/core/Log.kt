/*
* TODO: Remove when this and the other util classes are placed into a library module.
* */
@file:Suppress("MemberVisibilityCanBePrivate")

package com.gravitycode.caimito.kotlin.core

/**
 * Verbose: A non-critical event has succeeded. Also covers any event which does not belong at another level,
 * such as intermediate events (events which occur while a procedure is running which has not completed or
 * failed yet), or the initial execution of a procedure such as the beginning of a function call.
 *
 * Debug: A non-critical event has failed, e.g. a condition which is expected to not be met at times has not been.
 *
 * Info: An app critical event (one which will effect the user experience if it fails) has completed successfully.
 *
 * Warn: An app critical event has completed, but under conditions which may signal the presence of an
 * unrecognised error or bug. An exception may be raised down the line as the result of this unrecognised issue.
 *
 * Error: An app critical event has failed. An exception has been raised which must be recovered from gracefully.
 * */
object Log {

    const val META_TAG = "app: "

    /**
     * Log events which do not belong to any other log level; non-critical conditions succeeding, the state
     * of a particular procedure or module across time, lifecycle events, initial function calls etc.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * @param throwable An exception to log
     * */
    fun v(tag: String, message: String, throwable: Throwable? = null) {
        if (AppConfiguration.isDebug()) {
            android.util.Log.v("$META_TAG$tag", message, throwable)
        }
    }

    /**
     * Log events which do not belong to any other log level; non-critical conditions succeeding, the state
     * of a particular procedure or module across time, lifecycle events, initial function calls etc.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * */
    /*
    * `message: () -> String` allows for better formatting of long log messages in code. Not sure if there's
    * another reason Kotlin uses this pattern in it's standard libraries, but it's useful for this purpose.
    * */
    fun v(tag: String, message: () -> String) {
        v(tag, message())
    }

    /**
     * Log events in which non-critical conditions fail, e.g. an event was not triggered due to a certain
     * condition not being met.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * @param throwable An exception to log
     * */
    fun d(tag: String, message: String, throwable: Throwable? = null) {
        if (AppConfiguration.isDebug()) {
            android.util.Log.d("$META_TAG$tag", message, throwable)
        }
    }

    /**
     * Log events in which non-critical conditions fail, e.g. an event was not triggered due to a certain
     * condition not being met.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * */
    /*
    * `message: () -> String` allows for better formatting of long log messages in code. Not sure if there's
    * another reason Kotlin uses this pattern in it's standard libraries, but it's useful for this purpose.
    * */
    fun d(tag: String, message: () -> String) {
        d(tag, message())
    }

    /**
     * Log events which are crucial to the functioning of the application which have executed correctly.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * @param throwable An exception to log
     * */
    fun i(tag: String, message: String, throwable: Throwable? = null) {
        if (AppConfiguration.isDebug()) {
            android.util.Log.i("$META_TAG$tag", message, throwable)
        }
    }

    /**
     * Log events which are crucial to the functioning of the application which have executed correctly.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * */
    /*
    * `message: () -> String` allows for better formatting of long log messages in code. Not sure if there's
    * another reason Kotlin uses this pattern in it's standard libraries, but it's useful for this purpose.
    * */
    fun i(tag: String, message: () -> String) {
        i(tag, message())
    }

    /**
     * Log events which could be indicative of an error, but which have not actually raised an exception.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * @param throwable An exception to log
     * */
    fun w(tag: String, message: String, throwable: Throwable? = null) {
        if (AppConfiguration.isDebug()) {
            android.util.Log.w("$META_TAG$tag", message, throwable)
        }
    }

    /**
     * Log events which could be indicative of an error, but which have not actually raised an exception.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * */
    /*
    * `message: () -> String` allows for better formatting of long log messages in code. Not sure if there's
    * another reason Kotlin uses this pattern in it's standard libraries, but it's useful for this purpose.
    * */
    fun w(tag: String, message: () -> String) {
        w(tag, message())
    }

    /**
     * Log events which have or could have raised an exception.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * @param throwable An exception to log
     * */
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (AppConfiguration.isDebug()) {
            android.util.Log.e("$META_TAG$tag", message, throwable)
        }
    }

    /**
     * Log events which have or could have raised an exception.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * */
    /*
    * `message: () -> String` allows for better formatting of long log messages in code. Not sure if there's
    * another reason Kotlin uses this pattern in it's standard libraries, but it's useful for this purpose.
    * */
    fun e(tag: String, message: () -> String) {
        e(tag, message())
    }

    /**
     * Log system critical events which have not occurred as the result of a bug in code, but as a result of
     * a failure of the framework, the virtual machine, or some other external tool or dependency.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * @param throwable An exception to log
     * */
    fun wtf(tag: String, message: String, throwable: Throwable? = null) {
        if (AppConfiguration.isDebug()) {
            android.util.Log.wtf("$META_TAG$tag", message, throwable)
        }
    }

    /**
     * Log system critical events which have not occurred as the result of a bug in code, but as a result of
     * a failure of the framework, the virtual machine, or some other external tool or dependency.
     *
     * **IMPORTANT**: Logging will only take place if [AppConfiguration.isDebug] returns `true`.
     *
     * @param tag The log tag, which will be prepended with [META_TAG]
     * @param message The log message
     * */
    /*
    * `message: () -> String` allows for better formatting of long log messages in code. Not sure if there's
    * another reason Kotlin uses this pattern in it's standard libraries, but it's useful for this purpose.
    * */
    fun wtf(tag: String, message: () -> String) {
        wtf(tag, message())
    }
}