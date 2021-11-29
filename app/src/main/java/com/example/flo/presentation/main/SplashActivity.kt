package com.example.flo.presentation.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.data.ResponseSignData
import com.example.flo.data.api.ServiceCreator
import com.example.flo.databinding.ActivitySplashBinding
import com.example.flo.presentation.sign.SignInActivity
import com.example.flo.presentation.util.SharedPreferenceController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAutoLogin()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setAutoLogin() {
        val jwt = SharedPreferenceController.getJwt(this@SplashActivity)
        val call: Call<ResponseSignData> = ServiceCreator.signService.autoLogin(jwt!!)
        Log.d("로그인", jwt.toString())

        call.enqueue(object : Callback<ResponseSignData> {
            override fun onResponse(
                call: Call<ResponseSignData>,
                response: Response<ResponseSignData>
            ) {
                val loginResponse: ResponseSignData = response.body()!!
                when (loginResponse.code) {
                    1000 -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            Toast.makeText(this@SplashActivity, "자동로그인 되었습니다", Toast.LENGTH_SHORT)
                                .show()
                        }, 2000)
                    }
                    else -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                        }, 2000)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSignData>, t: Throwable) {
                Log.d("SignUpNetwork", "error:$t")
            }
        })
    }
}