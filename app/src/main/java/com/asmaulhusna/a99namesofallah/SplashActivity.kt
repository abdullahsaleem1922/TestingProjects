package com.asmaulhusna.a99namesofallah;

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asmaulhusna.a99namesofallah.AdsBillig.BillingUtilsIAP
import com.asmaulhusna.a99namesofallah.AdsBillig.Internet

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val internet = Internet()

        if (!BillingUtilsIAP.isPremium() && internet.isConnected(this) ) {//&& internet.isConnected(this)
            Toast.makeText(this,"billib",Toast.LENGTH_SHORT)
            val runnable = Runnable { showStartMethodWithAppOpen() }
            handler.postDelayed(runnable, 5000)
            App.openApp!!.fetchAd {
                handler.removeCallbacks(runnable)
                showStartMethodWithAppOpen()
                null
            }
        } else {
            val runnable = Runnable { moveToNext() }
            handler.postDelayed(runnable, 5000)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Hide the action bar if needed
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    private fun moveToNext() {
        Log.d("TAGG", "moveToNext: ")
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun showStartMethodWithAppOpen() {
        App.openApp!!.showAdIfAvailable {
            runOnUiThread { moveToNext() }
            null
        }
    }

    override fun onResume() {
        super.onResume()
    }

}