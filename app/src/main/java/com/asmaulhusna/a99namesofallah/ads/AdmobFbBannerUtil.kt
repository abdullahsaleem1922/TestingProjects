package com.asmaulhusna.a99namesofallah.ads

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

object AdmobFbBannerUtil {


    fun loadAdmobBannerOrFbBanner(context: Context, adContainer: LinearLayout , adId: String) {


        var mAdView: AdView
        try {
            mAdView = AdView(context)
            mAdView.adUnitId = adId//context.resources.getString(R.string.admob_banner)
            adContainer.addView(mAdView);
            val adRequest: AdRequest = AdRequest.Builder().build()
            val adSize = getAdSize(context)
            mAdView.setAdSize(adSize)
            mAdView.loadAd(adRequest)
            mAdView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    adContainer.visibility = View.VISIBLE
                    Log.d("AdTag", "Admob Banner Loaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("Ad Tag", "Admob Banner Failed to Load")
                    adContainer.visibility = View.INVISIBLE
                 }

                override fun onAdOpened() {
                }

                override fun onAdClicked() {
                }

                override fun onAdClosed() {
                }
            }
        } catch (ex: Exception) {

        }
    }

    private fun getAdSize(context: Context): AdSize {
        val display = (context as AppCompatActivity).windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }




}