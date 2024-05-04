package com.asmaulhusna.a99namesofallah;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.asmaulhusna.a99namesofallah.AdsBillig.BillingUtilsIAP;
import com.asmaulhusna.a99namesofallah.ads.ActionOnAdClosedListener;
import com.asmaulhusna.a99namesofallah.ads.AdMobInterstitial;
import com.asmaulhusna.a99namesofallah.ads.AdmobFbBannerUtil;
import com.asmaulhusna.a99namesofallah.ads.InterstitialClass;
import com.asmaulhusna.a99namesofallah.ads.InterstitialClosedListener;
import com.asmaulhusna.a99namesofallah.ads.InterstitialLoadListenerImplementer;
import com.google.android.gms.ads.interstitial.InterstitialAd;



public class ActivityListView extends AppCompatActivity {
    ImageView NameImage;
    ActivityListViewAdapter adapter;
    ListView list;
    TextView meaning;
    TextView name;
    RelativeLayout adslay;
    LinearLayout banner;

    private InterstitialAd interstitialAd;

    public ActivityListView() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(1);
        setContentView(R.layout.activity_list_view);

        adslay=findViewById(R.id.adslay);
        banner=findViewById(R.id.bannerAdContainer);
        if(!BillingUtilsIAP.isPremium())
        {
            AdmobFbBannerUtil.INSTANCE.loadAdmobBannerOrFbBanner(this,banner,getString(R.string.benner_ad));
        }
        else
        {
            adslay.setVisibility(View.GONE);
        }
        this.list = (ListView) findViewById(R.id.ListView);
        this.adapter = new ActivityListViewAdapter(getApplicationContext());
        this.list.setAdapter(this.adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                Log.e("Position", String.valueOf(i));
                Intent intent = new Intent(ActivityListView.this,ActivityListData.class);
                intent.putExtra("Position", i);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (!BillingUtilsIAP.isPremium()) {
            InterstitialClass.request_interstitial(ActivityListView.this,
                    ActivityListView.this,
                    getString(R.string.intrestitial_ad),
                    new ActionOnAdClosedListener() {
                        @Override
                        public void ActionAfterAd() {
                            finish();
                        }
                    });
        } else {
            finish();
        }
    }

    private void show_interstial() {

        if (AdMobInterstitial.isAlreadyLoaded) {
            AdMobInterstitial.showInterstitial(this);
            InterstitialLoadListenerImplementer.setOnInterstitialClosedMaster(new InterstitialClosedListener() {
                @Override
                public void onInterstitialClosed() {
                    finish();                }

                @Override
                public void onInterstitialFailedToShow() {
                    finish();                }
            });

        }
    }
}

