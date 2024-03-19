package com.gravitycode.caimito.kotlin.android

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Important to remember, the `activity` parameter which is passed in must be for an activity which
 * is still either in [ComponentActivity.onCreate] or [ComponentActivity.onStart]. If
 * [androidx.activity.result.ActivityResultRegistry.register] is called on an activity which has
 * already entered the RESUME state it will throw an exception. As a result, it is best to construct
 * `GetActivityResult` in the field of whatever class is using it, passing the activity instance
 * from that activity's [ComponentActivity.onCreate] or [ComponentActivity.onStart], or in the case
 * of Dagger, construct the component which is using the activity instance in [ComponentActivity.onCreate]
 * or [ComponentActivity.onStart].
 *
 * (Resolve "must call register before LifecycleOwner STARTED")[https://stackoverflow.com/questions/64476827/]
 * (Get activity result in other class)[https://developer.android.com/training/basics/intents/result#separate]
 * */
class GetActivityResult<I, O>(
    activity: ComponentActivity,
    contract: ActivityResultContract<I, O>,
) : ActivityResultCallback<O> {

    private val nextLocalRequestCode = AtomicInteger()
    private val launcher = activity.activityResultRegistry.register(
        "activity_rq#" + nextLocalRequestCode.getAndIncrement(),
        activity,
        contract,
        this
    )

    private lateinit var continuation: Continuation<O>

    override fun onActivityResult(result: O) {
        continuation.resume(result)
    }

    suspend operator fun invoke(i: I): O = suspendCoroutine { continuation ->
        this.continuation = continuation
        launcher.launch(i)
    }
}