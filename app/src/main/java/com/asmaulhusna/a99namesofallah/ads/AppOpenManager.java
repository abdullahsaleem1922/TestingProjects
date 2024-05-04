package com.asmaulhusna.a99namesofallah.ads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.asmaulhusna.a99namesofallah.App;
import com.asmaulhusna.a99namesofallah.MainActivity;
import com.asmaulhusna.a99namesofallah.R;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

/**
 * Prefetches App Open Ads.
 */
public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenManager";
    private static boolean isShowingAd = false;
    private final App myApplication;
    public Activity currentActivity;
    private AppOpenAd appOpenAd = null;
    private long loadTime = 0;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private boolean isShowingAd1 = false;
    private boolean loadFailed = true;
    private boolean showFailed = true;


    /**
     * Constructor
     */
    public AppOpenManager(App myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable();
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }

        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        Log.d(LOG_TAG, "Ad Loaded");
                        AppOpenManager.this.appOpenAd = ad;
                        AppOpenManager.this.loadTime = (new Date()).getTime();
                        if (!isShowingAd1) {
                            showAdIfAvailable1();
                        }

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Handle the error.
                        loadFailed = false;
                        Log.d(LOG_TAG, "Will show ad.");
                        currentActivity.startActivity(new Intent(currentActivity, MainActivity.class));
                        currentActivity.finish();
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication, currentActivity.getString(R.string.app_open_ad), request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    public void showAdIfAvailable() {
        if (!AdMobInterstitial.isSplashActivity) return;
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
//        CountDownTimer timer = new CountDownTimer(5000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//            @Override
//            public void onFinish() {
//                Log.d(LOG_TAG, "onFinish: ");
//                if (loadFailed && showFailed) {
//                    currentActivity.startActivity(new Intent(currentActivity, HomeActivity.class));
//                    currentActivity.finish();
//                }
//            }
//        };
//        timer.start();


        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Ad Shown.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            currentActivity.startActivity(new Intent(currentActivity, MainActivity.class));
                            currentActivity.finish();
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Log.d(LOG_TAG, "Failed to show.");
                            showFailed = false;
                            currentActivity.startActivity(new Intent(currentActivity, MainActivity.class));
                            currentActivity.finish();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);

        } else {
            Log.d(LOG_TAG, "Load Request Sent!");
            fetchAd();
        }
    }


    public void showAdIfAvailable1() {
        if (!AdMobInterstitial.isSplashActivity) return;
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd1 = true;
                            fetchAd();
                            currentActivity.startActivity(new Intent(currentActivity, MainActivity.class));
                            currentActivity.finish();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Log.e("testtag", "onAdFailedToShowFullScreenContent: " + adError.getMessage());
                            showFailed = false;
                            currentActivity.startActivity(new Intent(currentActivity, MainActivity.class));
                            currentActivity.finish();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd1 = true;
                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);

        } else {
            Log.d(LOG_TAG, "Load Request Sent!");
            fetchAd();
        }
    }


    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {
        Log.e("testtag", "onAppBackgrounded: is backgrounded");

    }
}
