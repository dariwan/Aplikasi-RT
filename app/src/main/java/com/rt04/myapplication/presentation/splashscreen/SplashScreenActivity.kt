package com.rt04.myapplication.presentation.splashscreen

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rt04.myapplication.databinding.ActivitySplashScreenBinding
import com.rt04.myapplication.presentation.login.LoginActivity
import com.rt04.myapplication.presentation.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        progressBarSetup()
    }

    private fun progressBarSetup() {
        binding.progressBar.max = 1000
        ObjectAnimator.ofInt(binding.progressBar, "progress", 1000)
            .setDuration(3000)
            .start()
    }

//    override fun onResume() {
//        super.onResume()
//        val sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE)
//        val isLogin = sharedPreferences.getBoolean(LoginActivity.IS_LOGIN, false)
//        if (isLogin) {
//            val intentToMain = Intent(this@SplashScreenActivity, MainActivity::class.java)
//            startActivity(intentToMain)
//            finish()
//        }
//    }
}