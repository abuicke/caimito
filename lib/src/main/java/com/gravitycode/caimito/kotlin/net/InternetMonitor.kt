package com.gravitycode.caimito.kotlin.net

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.gravitycode.caimito.kotlin.android.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.annotation.concurrent.ThreadSafe

private const val TAG = "InternetMonitor"

@ThreadSafe
interface InternetMonitor {

    companion object {

        private val LOCK = Any()

        @Volatile
        private var instance: InternetMonitor? = null

        fun getInstance(
            applicationScope: CoroutineScope,
            connectivityManager: ConnectivityManager
        ): InternetMonitor {
            return instance ?: synchronized(LOCK) {
                if (instance == null) {
                    instance = InternetMonitorImpl(applicationScope, connectivityManager)
                }

                instance!!
            }
        }
    }

    fun subscribe(): Flow<InternetState>
}

/**
 * This naive solution is the only thing I've found that works under all circumstances, including using an
 * always-on VPN. Every time there is a change in the network related to internet, I check to see if the
 * device is online. If multiple calls are made in quick succession they will all cause an online check, but
 * the checks will occur sequentially, one after another.
 *
 * TODO: (FROM [NetworkRequest] DOCUMENTATION) Also, starting with [Build.VERSION_CODES.UPSIDE_DOWN_CAKE],
 *  some capabilities require the application to self-certify by explicitly adding the
 *  [android.content.pm.PackageManager.PROPERTY_SELF_CERTIFIED_NETWORK_CAPABILITIES] property in the
 *  AndroidManifest.xml, which points to an XML resource file. In the XML resource file, the application
 *  declares what kind of network capabilities the application wants to have.
 */
private class InternetMonitorImpl(
    private val applicationScope: CoroutineScope,
    connectivityManager: ConnectivityManager
) : InternetMonitor {

    private val mutex = Mutex()
    private val sharedFlow = MutableSharedFlow<InternetState>(1)

    init {
        val internetRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            // .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
            .build()

        val internetCallback = NetworkChangedCallback {
            checkAndEmitInternetStatus()
        }

        connectivityManager.registerNetworkCallback(internetRequest, internetCallback)
    }

    override fun subscribe() = sharedFlow

    private suspend fun emit(state: InternetState) {
        Log.v(TAG, "emit($state)")

        val lastState = if (sharedFlow.replayCache.isNotEmpty()) {
            sharedFlow.replayCache.last()
        } else {
            null
        }

        Log.v(TAG, "last emitted state is $lastState")

        if (lastState != state) {
            sharedFlow.emit(state)
            Log.i(TAG, "emitted $state")
        } else {
            Log.d(TAG) {
                "not emitting state $state as it matches the last emitted state from the replay cache, " +
                        "replay cache = ${sharedFlow.replayCache}"
            }
        }
    }

    private fun checkAndEmitInternetStatus() {
        applicationScope.launch {
            mutex.withLock {
                if (isOnline()) {
                    emit(InternetState.ONLINE)
                } else {
                    emit(InternetState.OFFLINE)
                }
            }
        }
    }
}

private class NetworkChangedCallback(
    private val onNetworkChanged: () -> Unit
) : ConnectivityManager.NetworkCallback() {

    private companion object {

        private const val TAG = "NetworkChangedCallback"
    }

    override fun onAvailable(network: Network) {
        Log.v(TAG, "onAvailable($network)")
        onNetworkChanged()
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        Log.v(TAG, "onLosing($network, $maxMsToLive)")
        onNetworkChanged()
    }

    override fun onLost(network: Network) {
        Log.v(TAG, "onLost($network)")
        onNetworkChanged()
    }

    override fun onUnavailable() {
        Log.v(TAG, "onUnavailable()")
        onNetworkChanged()
    }
}