package com.asmaulhusna.a99namesofallah.ads.splash;

public class InterstitialLoadedListenerImplementerSplash {

    static InterstitialLoadedListenerSplash loadedListenerMaster;

    public static void setOnInterstitialLoadedMaster(InterstitialLoadedListenerSplash mLoadedListenerMaster) {
        loadedListenerMaster = mLoadedListenerMaster;
    }

    public static void onInterstitialLoadedCalled() {
        loadedListenerMaster.onInterstitialLoaded();
    }

    public static void onInterstitialFailedCalled() {
        loadedListenerMaster.onInterstitialFailed();
    }


}
