package com.asmaulhusna.a99namesofallah.AdsBillig

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

class Internet {

    @SuppressLint("ServiceCast")
    fun isConnected(context: Context): Boolean {
        var connected = true
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity", e.message!!)
        }
        return connected
    }
}