package com.aslibayar.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

class ConnectivityMonitor(context: Context) {

    init {
        // NetworkStateHolder'ı başlat
        NetworkStateHolder.initialize(context)

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // Bağlantı kurulduğunda durumu güncelle
                NetworkStateHolder.updateConnectionStatus(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // Bağlantı kaybolduğunda durumu güncelle
                NetworkStateHolder.updateConnectionStatus(false)
            }
        })
    }
}