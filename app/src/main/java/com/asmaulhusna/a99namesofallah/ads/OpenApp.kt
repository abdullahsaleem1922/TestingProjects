package com.asmaulhusna.a99namesofallah.ads;

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.asmaulhusna.a99namesofallah.App
import com.asmaulhusna.a99namesofallah.R
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import org.jetbrains.annotations.NotNull
import org.koin.core.component.KoinComponent


class OpenApp(globalClass: App) : Application.ActivityLifecycleCallbacks,
    LifecycleObserver, KoinComponent {

    private var appOpenAd: AppOpenAd? = null

    private var currentActivity: Activity? = null
    private var isShowingAd = false
    private var myApplication: App? = globalClass
    private var fullScreenContentCallback: FullScreenContentCallback? = null


    init {
        this.myApplication?.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    var isAdrequestSend = false
    fun fetchAd() {
        if (isAdAvailable() || isAdrequestSend) {
            return
        }


        isAdrequestSend = true
        val loadCallback: AppOpenAdLoadCallback = object : AppOpenAdLoadCallback() {

            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                isAdrequestSend = false
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
//                Timber.d(p0.message)
            }

        }
        val request: AdRequest = getAdRequest()

        myApplication?.applicationContext?.apply {

            AppOpenAd.load(
                this,
                getString(R.string.app_open_ad),
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback
            )

        }


    }

    fun fetchAd(onLoad: () -> Unit) {

        if (isAdAvailable()) {
            onLoad.invoke()
            return
        }
        if (isAdrequestSend) return

        isAdrequestSend = true
        val loadCallback: AppOpenAdLoadCallback = object : AppOpenAdLoadCallback() {

            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                isAdrequestSend = false
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
//                Timber.d(p0.message)
                onLoad.invoke()
            }

        }
        val request: AdRequest = getAdRequest()

        myApplication?.applicationContext?.apply {

            AppOpenAd.load(
                this,
                getString(R.string.app_open_ad),
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback
            )

        }


    }

    fun showAdIfAvailable(onAdClick: () -> Unit) {

        if (!isShowingAd && isAdAvailable() ) {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    appOpenAd = null
                    isShowingAd = false

                    // fetchAd()
                    onAdClick.invoke()

                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    onAdClick.invoke()
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true

                }
            }



            appOpenAd?.fullScreenContentCallback = fullScreenContentCallback
            appOpenAd!!.show(currentActivity!!)




        } else {
            onAdClick.invoke()
        }

    }


    @NotNull
    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }


    private fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }


    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }
}