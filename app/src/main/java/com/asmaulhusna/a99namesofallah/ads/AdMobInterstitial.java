package com.asmaulhusna.a99namesofallah.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.asmaulhusna.a99namesofallah.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdMobInterstitial {

    public static InterstitialAd mInterstitialAd;
    public static boolean isAlreadyLoaded = false;
    public static boolean isInterstitialShown = false;
    public static boolean isSplashActivity = false;
    static Context mContext;
    static String interstitial_id;
    static String logTag = "inter_";

    public static void loadInterstitialAd(Context your_activity_context, String your_interstitial_id) {
        mContext = your_activity_context;
        interstitial_id = your_interstitial_id;

        if (!isAlreadyLoaded) {

            Log.d(logTag, "Interstitial Load Request Sent.");
            AdRequest adRequest_interstitial = new AdRequest.Builder().build();

            InterstitialAd.load(mContext, your_interstitial_id, adRequest_interstitial, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
                    Log.d(logTag, "Interstitial Loaded.");
                    isAlreadyLoaded = true;
                    InterstitialLoadedListenerImplementer.onInterstitialLoadedCalled();
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.d(logTag, "Interstitial Failed to Load." + loadAdError.getMessage());
                    mInterstitialAd = null;
                    isAlreadyLoaded = false;
                    InterstitialLoadedListenerImplementer.onInterstitialFailedCalled();
                }
            });
        } else {
            Log.d(logTag, "Interstitial Already Loaded. Request not Sent.");
        }

    }


    public static void showInterstitial(Activity yourActivity) {

        if (isAlreadyLoaded) {
            mInterstitialAd.show(yourActivity);
            isAlreadyLoaded = false;

            Log.d(logTag, "Interstitial Shown.");
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
//                    if(!isBackPressed)
                    isInterstitialShown = false;
                    Log.d(logTag, "Interstitial Closed.");
                    loadInterstitialAd(mContext, yourActivity.getString(R.string.intrestitial_ad));
                    InterstitialLoadListenerImplementer.onInterstitialClosedCalled();

                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    isInterstitialShown = true;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    isInterstitialShown = false;
                    InterstitialLoadListenerImplementer.onInterstitialFailedToShowCalled();
                    Log.d(logTag, "Interstitial Closed.");

                }
            });
        } else {
            Log.d(logTag, "Interstitial was not Loaded.");
            isAlreadyLoaded = false;
            loadInterstitialAd(mContext, mContext.getString(R.string.intrestitial_ad));
        }
    }


}
