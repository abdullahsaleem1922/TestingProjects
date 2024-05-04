package com.asmaulhusna.a99namesofallah

import android.app.Application
import com.asmaulhusna.a99namesofallah.AdsBillig.BillingUtilsIAP
import com.asmaulhusna.a99namesofallah.ads.OpenApp
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        BillingUtilsIAP(this)
        openApp = OpenApp(this)
        initFcm()
    }

    private fun initFcm() {
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("all").addOnCompleteListener { }
    }

    companion object {
        var openApp: OpenApp? = null
    }
}