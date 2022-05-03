package com.example.appmeli

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getSupportActionBar()?.hide();
        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)
    }
}