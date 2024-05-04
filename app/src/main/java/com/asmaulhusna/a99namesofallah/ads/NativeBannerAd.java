package com.asmaulhusna.a99namesofallah.ads;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asmaulhusna.a99namesofallah.R;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;


public class NativeBannerAd {
    public static void loadNativeAd(
            Context context,
            int numberOfAds ,
            String adId ,
            int adChoicePlacement ,
            NativeAd.OnNativeAdLoadedListener loadlistner
    )
    {

        AdLoader adLoader = new AdLoader.Builder(context, adId)
                .forNativeAd(loadlistner)
                .withNativeAdOptions(new NativeAdOptions.Builder()

                        .setAdChoicesPlacement(adChoicePlacement)
                        .build()
                ).build();
        adLoader.loadAds(new AdRequest.Builder().build(), numberOfAds);
    }
    public static void inflateNativeAd(NativeAd nativeAd, NativeAdView nativeAdview) {
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
            ad_call_to_action.setText( nativeAd.getCallToAction());
            nativeAdview.setCallToActionView(ad_call_to_action);
        }
        nativeAdview.setNativeAd(nativeAd);
        nativeAdview.setVisibility(View.VISIBLE);
    }
}
