package com.example.network_check

enum class NetworkStatus {
    Connected,
    NotConnected,
}

enum class NetworkMode {
    Wifi,
    Cellular,
    None,
}

class NetworkModel(final val status: NetworkStatus, final val connectedTo: NetworkMode) {

    fun toMap() : Map<String,Any>{
        return mapOf<String,Any>(
            "status" to status.name,
            "connectedTo" to connectedTo.name,
        )
    }
}