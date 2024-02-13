package com.example.network_check

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkConnector {
    private val notConnected = NetworkModel(NetworkStatus.NotConnected,NetworkMode.None)

     suspend fun networkStatus (context: Context) : NetworkModel {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                targetApiM(connectivityManager,context)
            }else{
                targetApiLow(connectivityManager,context)
            }
        }catch (e:Error){
            println("Error fetching network status $e")
            notConnected
        }
     }

    @OptIn(DelicateCoroutinesApi::class)
    @TargetApi(Build.VERSION_CODES.M)
    private suspend fun targetApiM(connectivityManager : ConnectivityManager, context: Context) : NetworkModel {
        val currentNetwork = connectivityManager.activeNetwork
            ?: return notConnected
        val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
            ?: return notConnected
        var networkMode : NetworkMode = NetworkMode.None
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            networkMode = NetworkMode.Phone
        }
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
            networkMode = NetworkMode.Wifi
        }
        val isReachable = withContext(Dispatchers.IO) {
            return@withContext NetworkReachability().hasInternetConnected()
        }
        if (isReachable) {
            return NetworkModel(NetworkStatus.Connected,networkMode)
        }
        return notConnected
    }

    @Suppress("DEPRECATION")
    private suspend fun targetApiLow(connectivityManager : ConnectivityManager, context: Context) : NetworkModel {
        val currentNetwork = connectivityManager.activeNetworkInfo
            ?: return notConnected
        var networkMode : NetworkMode = NetworkMode.None
        if (currentNetwork.type ==  (NetworkCapabilities.TRANSPORT_CELLULAR)){
            networkMode = NetworkMode.Phone
        }
        else if (currentNetwork.type ==  (NetworkCapabilities.TRANSPORT_WIFI)){
            networkMode = NetworkMode.Wifi
        }
        val isReachable = withContext(Dispatchers.IO) {
            return@withContext NetworkReachability().hasInternetConnected()
        }
        if (isReachable) {
            return NetworkModel(NetworkStatus.Connected,networkMode)
        }
        return notConnected
    }

}

