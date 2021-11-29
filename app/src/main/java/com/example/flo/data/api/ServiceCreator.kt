package com.example.flo.data.api

import android.util.Log
import com.example.flo.data.Auth
import com.example.flo.data.ResponseSignData
import com.example.flo.data.User
import com.example.flo.data.api.view.LoginView
import com.example.flo.data.api.view.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://13.125.121.202"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val signService: SignService = retrofit.create(SignService::class.java)
    val musicService: MusicService = retrofit.create(MusicService::class.java)
}

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user: User) {
        val call: Call<ResponseSignData> = ServiceCreator.signService.postSignUp(user)

        signUpView.onSignUpLoading()

        call.enqueue(object : Callback<ResponseSignData> {
            override fun onResponse(
                call: Call<ResponseSignData>,
                response: Response<ResponseSignData>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val signUpResponse: ResponseSignData = response.body()!!

                    when (signUpResponse.code) {
                        1000 -> signUpView.onSignUpSuccess()
                        else -> signUpView.onSignUpFailure(
                            signUpResponse.code,
                            signUpResponse.message
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSignData>, t: Throwable) {
                Log.d("SignUpNetwork", "error:$t")
                signUpView.onSignUpFailure(400, "네트워크 오류가 발생했습니다")
            }
        })
    }

    fun login(user: User) {
        val call: Call<ResponseSignData> = ServiceCreator.signService.postLogin(user)

        loginView.onLoginLoading()

        call.enqueue(object : Callback<ResponseSignData> {
            override fun onResponse(
                call: Call<ResponseSignData>,
                response: Response<ResponseSignData>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val signInResponse: ResponseSignData = response.body()!!

                    when (signInResponse.code) {
                        1000 -> loginView.onLoginSuccess(signInResponse.result!!)
                        else -> loginView.onLoginFailure(
                            signInResponse.code,
                            signInResponse.message
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSignData>, t: Throwable) {
                Log.d("SignInNetwork", "error:$t")
                loginView.onLoginFailure(400, "네트워크 오류가 발생했습니다")
            }
        })
    }
}
