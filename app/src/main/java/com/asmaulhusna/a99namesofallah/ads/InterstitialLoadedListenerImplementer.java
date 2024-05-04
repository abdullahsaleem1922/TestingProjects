package com.asmaulhusna.a99namesofallah.ads;

public class InterstitialLoadedListenerImplementer {

    static InterstitialLoadedListener loadedListenerMaster;

    public static void setOnInterstitialLoadedMaster(InterstitialLoadedListener mLoadedListenerMaster) {
        loadedListenerMaster = mLoadedListenerMaster;
    }

    public static void onInterstitialLoadedCalled() {
        loadedListenerMaster.onInterstitialLoaded();
    }

    public static void onInterstitialFailedCalled() {
        loadedListenerMaster.onInterstitialFailed();
    }


}
