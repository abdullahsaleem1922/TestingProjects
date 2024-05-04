package com.asmaulhusna.a99namesofallah.ads;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.asmaulhusna.a99namesofallah.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;


public class AdmobNativeAds {
    private static int adChoicePlacement;

    public static void loadNativeAd(
            Context context,
            CardView admobMediumNative,
            int numberOfAds,
            String adId,
            int adchoicesTopLeft, com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener loadlistner
    ) {
        AdListener adListener = new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                admobMediumNative.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                admobMediumNative.setVisibility(View.INVISIBLE);
            }

        };
        AdLoader adLoader = new AdLoader.Builder(context, adId)
                .forNativeAd(loadlistner).withAdListener(adListener)
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .setAdChoicesPlacement(adChoicePlacement)
                        .build()
                ).build();
        adLoader.loadAds(new AdRequest.Builder().build(), numberOfAds);
    }

    public static void inflateNativeAd(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView nativeAdview) {
        TextView tvAdTitle = nativeAdview.findViewById(R.id.ad_headline);
        ImageView adIcon = nativeAdview.findViewById(R.id.ad_app_icon);
        TextView tvAdPrice = nativeAdview.findViewById(R.id.ad_body);
        TextView ad_call_to_action = nativeAdview.findViewById(R.id.ad_call_to_action);
        tvAdTitle.setText(nativeAd.getHeadline());
        if (nativeAd.getBody() != null) {
            tvAdPrice.setText(nativeAd.getBody());
        } else {
            tvAdPrice.setVisibility(View.GONE);
        }
        if (nativeAd.getIcon() == null)
            adIcon.setVisibility(View.GONE);
        else {
            adIcon.setVisibility(View.VISIBLE);
            adIcon.setImageDrawable(nativeAd.getIcon().getDrawable());
        }
        if (nativeAd.getCallToAction() == null)
            ad_call_to_action.setVisibility(View.GONE);
        else {
            ad_call_to_action.setVisibility(View.VISIBLE);
            ad_call_to_action.setText(nativeAd.getCallToAction());
            nativeAdview.setCallToActionView(ad_call_to_action);
        }
        nativeAdview.setNativeAd(nativeAd);
        nativeAdview.setVisibility(View.VISIBLE);
    }

    public static void inflateNativeAd_with_media(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView nativeAdview) {
//            NativeAdView nativeAdview = findViewById(R.id.native_ad);
        TextView tvAdTitle = nativeAdview.findViewById(R.id.ad_headline);
        ImageView adIcon = nativeAdview.findViewById(R.id.ad_app_icon);
        TextView tvAdPrice = nativeAdview.findViewById(R.id.ad_body);
        TextView ad_call_to_action = nativeAdview.findViewById(R.id.ad_call_to_action);
        com.google.android.gms.ads.nativead.MediaView mediaView = nativeAdview.findViewById(R.id.ad_media);
        tvAdTitle.setText(nativeAd.getHeadline());
        nativeAdview.setMediaView(mediaView);
        mediaView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (child instanceof ImageView) {
                    ((ImageView) child).setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }

        });


        if (nativeAd.getBody() != null) {
            tvAdPrice.setText(nativeAd.getBody());
        } else {
            tvAdPrice.setVisibility(View.GONE);
        }
        if (nativeAd.getIcon() == null)
            adIcon.setVisibility(View.GONE);
        else {
            adIcon.setVisibility(View.VISIBLE);
            adIcon.setImageDrawable(nativeAd.getIcon().getDrawable());
        }
        if (nativeAd.getCallToAction() == null)
            ad_call_to_action.setVisibility(View.GONE);
        else {
            ad_call_to_action.setVisibility(View.VISIBLE);
            ad_call_to_action.setText(nativeAd.getCallToAction());
            nativeAdview.setCallToActionView(ad_call_to_action);
        }
        nativeAdview.setNativeAd(nativeAd);
        nativeAdview.setVisibility(View.VISIBLE);
    }




}
