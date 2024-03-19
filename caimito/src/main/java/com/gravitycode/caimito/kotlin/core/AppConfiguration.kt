package com.gravitycode.caimito.kotlin.core

object AppConfiguration {

    private var debug: Boolean? = null
    private var crashOnError: Boolean? = null

    fun setup(debug: Boolean, crashOnError: Boolean) {
        if ((AppConfiguration.debug != null) or (AppConfiguration.crashOnError != null)) {
            throw IllegalStateException(
                "setup called twice, setup can only be called once per application lifetime"
            )
        }

        AppConfiguration.debug = debug
        AppConfiguration.crashOnError = crashOnError
    }

    fun isDebug(): Boolean {
        if (debug == null) {
            throw IllegalStateException("setup never called")
        }

        return debug!!
    }

    fun isProduction() = !isDebug()

    fun shouldCrashOnError(): Boolean {
        if (crashOnError == null) {
            throw IllegalStateException("setup never called")
        }

        return crashOnError!!
    }
}