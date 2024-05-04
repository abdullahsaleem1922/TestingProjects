package com.asmaulhusna.a99namesofallah.ads;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.asmaulhusna.a99namesofallah.AdsBillig.BillingUtilsIAP;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialClass {
    static InterstitialAd mInterstitialAd;
    static Context mContext;
    static Activity mActivity;
    static String mInterstitialID;
    static String logTag = "Ads_";
    static ActionOnAdClosedListener mActionOnAdClosedListener;
    static boolean isAdDecided = false;
    static int DELAY_TIME = 0;
    public static Boolean isInterstitalIsShowing = false;

    static boolean stopInterstitial = false;
    static boolean timerCalled = false;

    public static void request_interstitial(Context context, Activity activity, String interstitial_id, ActionOnAdClosedListener actionOnAdClosedListenersm) {
        mContext = context;
        mActivity = activity;
        mInterstitialID = interstitial_id;
        mActionOnAdClosedListener = actionOnAdClosedListenersm;
        isAdDecided = false;

        if (AdTimerClass.isEligibleForAd()) {
            if (!BillingUtilsIAP.isPremium()) {
                load_interstitial();
            }
            else {
                performAction();
            }

        } else {
            performAction();
        }

    }

    public static void load_interstitial() {


        if (mInterstitialAd == null){
            showAdDialog();
            stopInterstitial = false;
            timerCalled = false;
            AdRequest adRequest_interstitial = new AdRequest.Builder().build();
            InterstitialAd.load(mContext, mInterstitialID, adRequest_interstitial,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            isAdDecided = true;
                            Log.d(logTag, "Insterstitial Loaded.");

                            if (!timerCalled){
                              closeAdDialog();
                                show_interstitial();
                            }


                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d(logTag, "Interstitial Failed to Load." + loadAdError.getMessage());
                            mInterstitialAd = null;
                            isAdDecided = true;
                            if (!timerCalled){
                              closeAdDialog();
                                performAction();
                            }

                        }
                    });
            timerAdDecided();
        }else {
            Log.d(logTag, "Ad was already loaded.: ");
            stopInterstitial=false;
            showAdDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   closeAdDialog();
                    show_interstitial();
                }
            }, 2000);
        }


    }

    static void timerAdDecided() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAdDecided) {
                    stopInterstitial = true;
                    timerCalled=true;
                    Log.d(logTag, "Handler Cancel." );
                    AdTimerClass.cancelTimer();
                    closeAdDialog();
                    show_interstitial();
                }
            }
        }, 5000);
    }

    static ProgressDialog progressDialog;
    static AlertDialog alertDialog;
    static void showAdDialog() {
        isInterstitalIsShowing = true;

        if (Build.VERSION.SDK_INT >= 21){
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Please Wait.");
            progressDialog.setMessage("Full Screen Ad is expected to Show.");
            progressDialog.setCancelable(false);
            progressDialog.create();
            progressDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Please Wait.");
            builder.setMessage("Full Screen Ad is expected to Show.");
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            try {
                alertDialog.show();
            } catch (Exception e) {
                Log.e(logTag, "Error showing dialog: " + e.getMessage());
            }
        }
    }

    static void closeAdDialog() {
        isInterstitalIsShowing = false;
        if (Build.VERSION.SDK_INT >= 21) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null; // Set to null after dismissal to avoid memory leaks.
            } else if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog = null; // Set to null after dismissal to avoid memory leaks.
            }
        } else {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog = null; // Set to null after dismissal to avoid memory leaks.
            }
        }
    }



    static void show_interstitial() {
        if (mInterstitialAd != null && stopInterstitial == false) {
            isInterstitalIsShowing = true;
            mInterstitialAd.show(mActivity);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Log.d(logTag, "Insterstitial Failed to Show.");
                    mInterstitialAd = null;
                    isInterstitalIsShowing = false;
                    performAction();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.d(logTag, "Insterstitial Shown.");
                    mInterstitialAd = null;
                    isInterstitalIsShowing = false;
                    performAction();
                }
            });
        } else {
            performAction();
        }
    }

    static void performAction() {
        isInterstitalIsShowing = false;
        mActionOnAdClosedListener.ActionAfterAd();
    }
}
