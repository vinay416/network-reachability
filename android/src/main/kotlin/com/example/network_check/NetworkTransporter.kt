package com.example.network_check

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkTransporter {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun current(network : Network,connectivityManager: ConnectivityManager)
            : NetworkMode? {
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return null

        return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            NetworkMode.Cellular
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
            NetworkMode.Wifi
        } else {
            NetworkMode.None
        }
    }
}