package com.example.network_check

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetworkReachability {
    private val url = "https://example.com/"
    private val TAG: String = this::class.java.simpleName


    suspend fun hasInternetConnected(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "ConnectionTest")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1000 // configurable
                connection.connect()
//                Log.i(TAG, "hasInternetConnected: ${(connection.responseCode == 200)}")
                (connection.responseCode == 200)
            } catch (e: IOException) {
                Log.e(TAG, "Error checking internet connection : $e")
                false
            }
        }
    }
}
