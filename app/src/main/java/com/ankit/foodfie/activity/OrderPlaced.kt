package com.ankit.foodfie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ankit.foodfie.R

class OrderPlaced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)

        Handler().postDelayed({
            startActivity(Intent(this@OrderPlaced, HomeActivity::class.java))
            finish()
        },2000)
    }
}