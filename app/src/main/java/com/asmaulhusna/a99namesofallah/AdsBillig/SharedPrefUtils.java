package com.asmaulhusna.a99namesofallah.AdsBillig;

import android.content.Context;
import android.content.SharedPreferences;


public final class SharedPrefUtils {

    private static SharedPrefUtils sharedPrefUtils;
    private final SharedPreferences sharedPreferences;

    public SharedPrefUtils(Context context) {

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("InclinoMeterPref", 0);

        this.sharedPreferences = sharedPreferences2;
    }

    public static final SharedPrefUtils getInstance(Context context) {

        synchronized (SharedPrefUtils.class) {
            if (SharedPrefUtils.sharedPrefUtils == null) {
                SharedPrefUtils.sharedPrefUtils = new SharedPrefUtils(context);
            }

        }
        return SharedPrefUtils.sharedPrefUtils;
    }

    public final boolean getPremium() {
        return this.sharedPreferences.getBoolean("premium", false);
    }

    public final void setPremium(boolean z) {
        this.sharedPreferences.edit().putBoolean("premium", z).apply();
    }

    public final boolean getSoundUnlocked() {
        return this.sharedPreferences.getBoolean("SOUNDS_UNLOCKED", false);
    }

    public final void setSoundUnlocked(boolean z) {
        this.sharedPreferences.edit().putBoolean("SOUNDS_UNLOCKED", z).apply();
    }

    public final int getLanguage() {
        return this.sharedPreferences.getInt("SELECTED_LANG", -1);
    }

    public final void setLanguage(int i) {
        this.sharedPreferences.edit().putInt("SELECTED_LANG", i).apply();
    }

}
