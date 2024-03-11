package com.gravitycode.caimito.kotlin.android

import android.content.Context
import android.os.Build
import android.os.Process
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.os.strictmode.Violation
import androidx.annotation.RequiresApi
import com.gravitycode.caimito.kotlin.AppConfiguration
import java.util.concurrent.Executor

private const val TAG = "AndroidUtils"

/**
 * Only run the selected code if in debug, i.e. [AppConfiguration.isDebug] returns `true`.
 * */
fun debug(block: () -> Unit) {
    if (AppConfiguration.isDebug()) {
        block()
    }
}

/**
 * Run the specified [block] only if the build version is at least [sdk]
 * */
fun sdk(sdk: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= sdk) {
        block()
    }
}

/**
 * If an app is too chatty, Logcat will drop logs.
 *
 * See: (Missing logs on Android logcat)[https://stackoverflow.com/questions/60570015/] and
 * (android logcat logs chatty module line expire message)[https://stackoverflow.com/questions/34587273/]
 * */
fun disableLogcatThrottling() {
    val pid = Process.myPid()
    val whiteList = "logcat -P '$pid'"
    Runtime.getRuntime().exec(whiteList).waitFor()
}

/**
 * Enable all available strict mode policies for both [ThreadPolicy] and [VmPolicy].
 *
 * Only throws an exception if the root package of this application is included in the
 * stacktrace, otherwise just prints the relevant stacktrace with log level [android.util.Log.ERROR].
 * */
@RequiresApi(Build.VERSION_CODES.P)
fun enableStrictMode(context: Context, listenerExecutor: Executor) {

    val penaltyListener: (Violation) -> Unit = { violation ->
        val violationOriginatesFromApp =
            violation.stackTrace.fold(false) { acc, stackTraceElement ->
                val containsPackageName = stackTraceElement.toString().contains(context.packageName)
                acc || containsPackageName
            }

        if (violationOriginatesFromApp) {
            throw violation
        } else {
            Log.e("policy violation", android.util.Log.getStackTraceString(violation))
        }
    }

    StrictMode.setThreadPolicy(
        ThreadPolicy.Builder()
            .detectAll()
            .penaltyListener(listenerExecutor, penaltyListener)
            .build()
    )

    StrictMode.setVmPolicy(
        VmPolicy.Builder()
            .penaltyListener(listenerExecutor, penaltyListener)
            .detectAll()
            .build()
    )
}

private val DISABLE_STRICT_MODE_LOCK = Any()

/**
 * All strict mode policies will be disabled while [block] executes.
 *
 * Doesn't seem to be thread-safe and I don't know why. I don't currently have time to deal with it, and
 * thus far strict mode has caused me nothing but problems and provided no actual value. So I'm abandoning
 * it for now, but keeping the code here in case I want to take this back up in the future.
 * */
@Deprecated("potentially broken, threading issues")
fun <T> temporarilyDisableStrictMode(block: () -> T): T {
    if (!AppConfiguration.isProduction()) {
        return block()
    }

    synchronized(DISABLE_STRICT_MODE_LOCK) {
        val savedThreadPolicy = StrictMode.getThreadPolicy()
        val savedVmPolicy = StrictMode.getVmPolicy()

        Log.v(TAG, "temporarily disabling strict mode (${Thread.currentThread().id})")

        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())
        // TODO: No permitAll() for VmPolicy.Builder. Not sure if this is the same thing.
        StrictMode.setVmPolicy(VmPolicy.Builder().build())

        Log.v(TAG, "strict mode disabled (${Thread.currentThread().id})")

        val t = block()

        StrictMode.setThreadPolicy(savedThreadPolicy)
        StrictMode.setVmPolicy(savedVmPolicy)

        Log.v(TAG, "strict mode re-enabled (${Thread.currentThread().id})")

        return t
    }
}