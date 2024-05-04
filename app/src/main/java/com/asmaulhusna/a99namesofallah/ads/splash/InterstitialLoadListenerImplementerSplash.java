package com.asmaulhusna.a99namesofallah.ads.splash;

public class InterstitialLoadListenerImplementerSplash {
    static InterstitialClosedListenerSplash closedListener;

    public static void setOnInterstitialClosedMaster(InterstitialClosedListenerSplash mClosedListenerMaster) {
        closedListener = mClosedListenerMaster;
    }

    public static void onInterstitialClosedCalled() {
        closedListener.onInterstitialClosed();
    }

    public static void onInterstitialFailedToShowCalled() {
        closedListener.onInterstitialFailedToShow();
    }


}

