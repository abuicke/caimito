package com.gravitycode.caimito.kotlin.net

import com.gravitycode.caimito.kotlin.android.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

private const val TAG = "NetUtils"

/**
 * Test if this device currently has access to the internet.
 *
 * @param timeout Length of time in milliseconds to wait before aborting the attempt.
 * @param hostname The host to connect to, to verify internet accessibility. Should be reliably available.
 *
 * @return `true` if this device is connected to the internet, otherwise `false`
 *
 * See: [https://stackoverflow.com/questions/1560788/]
 *
 * TODO: This code could be improved by detecting if hostname isn't reached due to a 404 or other relevant
 *  HTTP error and try another reliable hostname.
 * */
suspend fun isOnline(timeout: Int = 1500, hostname: String = "8.8.8.8"): Boolean {
    return try {
        Socket().use { sock ->
            withContext(Dispatchers.IO) {
                Log.i(TAG, "attempting to connect to $hostname")
                // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
                val socketAddress = InetSocketAddress(hostname, 53)
                val startTimeMs = System.currentTimeMillis()
                sock.connect(socketAddress, timeout)
                val endTimeMs = System.currentTimeMillis()
                val elapsedTimeSeconds = (endTimeMs - startTimeMs).toFloat().div(1000)
                Log.i(TAG, "connection to $hostname succeeded after $elapsedTimeSeconds seconds")
            }
            true
        }
    } catch (t: Throwable) {
        Log.e(TAG, "connection to $hostname failed\n${t.message}")
        false
    }
}