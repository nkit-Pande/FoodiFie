package com.ankit.foodfie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ankit.foodfie.R
import com.ankit.foodfie.R.layout.activity_splash_art

class SplashArt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_splash_art)
        Handler().postDelayed({
            startActivity(Intent(this@SplashArt, LoginActivity::class.java))
            finish()
        },2000)
    }
}