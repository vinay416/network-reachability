package com.example.network_check

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NetworkConnector (context: Context) {
    private val TAG: String = this::class.java.simpleName
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    suspend fun networkStatus() : NetworkModel? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                targetApiM()
            }else{
                throw Error("Android 5.0 and below not supported")
            }
        }catch (e:Error){
            Log.e(TAG,"Error fetching network status $e")
            null
        }
     }

    @OptIn(DelicateCoroutinesApi::class)
    @TargetApi(Build.VERSION_CODES.M)
    private suspend fun targetApiM() : NetworkModel? {
        val currentNetwork = connectivityManager.activeNetwork
            ?: return null
        val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
            ?: return null
        var networkMode : NetworkMode = NetworkMode.None
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            networkMode = NetworkMode.Cellular
        }
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
            networkMode = NetworkMode.Wifi
        }
        val isReachable = withContext(Dispatchers.IO) {
            return@withContext NetworkReachability().hasInternetConnected()
        }
        return if (isReachable) {
            NetworkModel(NetworkStatus.Connected,networkMode)
        } else {
            NetworkModel(NetworkStatus.NotConnected,networkMode)
        }
    }

//    @Suppress("DEPRECATION")
//    private suspend fun targetApiLow() : NetworkModel? {
//        val currentNetwork = connectivityManager.activeNetworkInfo
//            ?: return null
//        var networkMode : NetworkMode = NetworkMode.None
//        if (currentNetwork.type ==  (NetworkCapabilities.TRANSPORT_CELLULAR)){
//            networkMode = NetworkMode.Cellular
//        }
//        else if (currentNetwork.type ==  (NetworkCapabilities.TRANSPORT_WIFI)){
//            networkMode = NetworkMode.Wifi
//        }
//        val isReachable = withContext(Dispatchers.IO) {
//            return@withContext NetworkReachability().hasInternetConnected()
//        }
//        return if (isReachable) {
//            NetworkModel(NetworkStatus.Connected,networkMode)
//        } else {
//            NetworkModel(NetworkStatus.NotConnected,networkMode)
//        }
//    }

}

