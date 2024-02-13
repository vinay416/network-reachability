package com.example.network_check

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetworkReachability {
    private val url = "https://www.google.com"

    suspend fun hasInternetConnected(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "ConnectionTest")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 5000 // configurable
                connection.connect()
//                println("hasInternetConnected: ${(connection.responseCode == 200)}")
                (connection.responseCode == 200)
            } catch (e: IOException) {
                println("Error checking internet connection : $e")
                false
            }
        }
    }
}
