package com.asmaulhusna.a99namesofallah.ads;

public class InterstitialLoadListenerImplementer {
    static InterstitialClosedListener closedListener;

    public static void setOnInterstitialClosedMaster(InterstitialClosedListener mClosedListenerMaster) {
        closedListener = mClosedListenerMaster;
    }

    public static void onInterstitialClosedCalled() {
        closedListener.onInterstitialClosed();
    }

    public static void onInterstitialFailedToShowCalled() {
        closedListener.onInterstitialFailedToShow();
    }


}

