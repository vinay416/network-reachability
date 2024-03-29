package com.example.network_check

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import io.flutter.plugin.common.EventChannel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NetworkStreamHandler (context: Context) : EventChannel.StreamHandler {
    private val TAG: String = this::class.java.simpleName


    // Declare our eventSink later it will be initialized
    private var eventSink: EventChannel.EventSink? = null
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val uiThreadHandler: Handler = Handler(Looper.getMainLooper())
    private var networkMode: NetworkMode = NetworkMode.None
    private var debugMode: Boolean = false
    private var networkTransport = NetworkTransporter()


    override fun onListen(p0: Any?, sink: EventChannel.EventSink) {
        eventSink = sink
        val dataMap = p0 as Map<*, *>
        debugMode = dataMap["debugMode"] as Boolean? ?: false
        try {
            if (debugMode) {
                Log.d(ContentValues.TAG, "network stream listening")
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                streamTargetApiN()
            }else{
                throw Error("Android 5.0 and below not supported")
            }
        } catch (e:Error){
            val message = "Error fetching network status"
            sink.error("-1",message,e)
            if (debugMode) {
                Log.e(TAG, "$message $e")
            }
        }
    }

    override fun onCancel(p0: Any?) {
        if (debugMode) {
            Log.d(ContentValues.TAG, "network stream closed")
        }
        eventSink = null
    }

    @OptIn(DelicateCoroutinesApi::class)
    @TargetApi(Build.VERSION_CODES.N)
    private fun streamTargetApiN() {
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network : Network) {
                if (debugMode) {
                    Log.i(TAG, "onAvailable: $network")
                }
            }

            override fun onLost(network : Network) {
                if (debugMode) {
                    Log.i(TAG, "onLost: + $network")
                }
                val data = NetworkModel(NetworkStatus.NotConnected, NetworkMode.None)
                uiThreadHandler.post { eventSink?.success(data.toMap()) }
            }

            override fun onCapabilitiesChanged(network : Network, capabilities : NetworkCapabilities) {
                if (debugMode) {
                    Log.i(TAG, "onCapabilitiesChanged: + $capabilities")
                }
                val networkTransport = networkTransport.current(network,connectivityManager)
                if(networkTransport==null) {
                    uiThreadHandler.post {
                        eventSink?.error("-1", "Network Transport", capabilities)
                    }
                }else {
                    networkMode = networkTransport
                    val data = NetworkModel(NetworkStatus.NotConnected, networkMode)
                    uiThreadHandler.post { eventSink?.success(data.toMap()) }
                    //checking https reachability
                    GlobalScope.launch { addEvent(networkTransport) }
                }
            }

            override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
                if (debugMode) {
                    Log.i(TAG, "onLinkPropertiesChanged: $linkProperties")
                }
            }

        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun addEvent(networkMode : NetworkMode){
        val isReachable = withContext(Dispatchers.IO) {
            return@withContext NetworkReachability().hasInternetConnected()
        }
        val networkData = if (isReachable) {
            NetworkModel(NetworkStatus.Connected,networkMode)
        } else {
            NetworkModel(NetworkStatus.NotConnected,networkMode)
        }
        uiThreadHandler.post { (eventSink?.success(networkData.toMap())) }
    }

}


