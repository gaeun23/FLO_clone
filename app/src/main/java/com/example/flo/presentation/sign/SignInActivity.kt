package com.example.flo.presentation.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.data.Auth
import com.example.flo.data.SongDataBase
import com.example.flo.data.User
import com.example.flo.data.api.AuthService
import com.example.flo.data.api.LoginView
import com.example.flo.databinding.ActivitySignInBinding
import com.example.flo.presentation.main.MainActivity
import com.example.flo.presentation.util.KeyBoardUtil
import com.example.flo.presentation.util.SharedPreferenceController

class SignInActivity : AppCompatActivity(), LoginView {
    private lateinit var binding: ActivitySignInBinding
    private var emailValidity: Boolean = false
    private var passwordValidity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCloseBtnClickListener()
        setSignUpBtnClickListener()
        setLoginBtnClickListener()
    }

    private fun setCloseBtnClickListener() {
        binding.signInBtnCloseIb.setOnClickListener {
            finish()
            KeyBoardUtil.hide(this)
        }
    }

    private fun setSignUpBtnClickListener() {
        binding.signUpBtnTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun setLoginBtnClickListener() {
        binding.signInBtnLogin.setOnClickListener {
            setLoginValid()
            setLoginDB()
        }
    }

    private fun setLoginValid() {
        when (binding.signInEmailTv.text.isNullOrEmpty() || binding.signInDomainTv.text.isNullOrEmpty()) {
            true -> {
                binding.signInEmailTv.setBackgroundResource(R.drawable.border_red_under_line)
                binding.signInDomainTv.setBackgroundResource(R.drawable.border_red_under_line)
                binding.signInEmailCheckMsgTv.visibility = View.VISIBLE
                emailValidity = false
            }
            else -> {
                binding.signInEmailTv.setBackgroundResource(R.drawable.sign_et_bg_selector)
                binding.signInDomainTv.setBackgroundResource(R.drawable.sign_et_bg_selector)
                binding.signInEmailCheckMsgTv.visibility = View.INVISIBLE
                emailValidity = true
            }
        }

        when (binding.signInPasswordTv.text.isNullOrEmpty()) {
            true -> {
                binding.signInPasswordTv.setBackgroundResource(R.drawable.border_red_under_line)
                binding.signInPwCheckMsgTv.visibility = View.VISIBLE
                passwordValidity = false
            }
            else -> {
                binding.signInPasswordTv.setBackgroundResource(R.drawable.sign_et_bg_selector)
                binding.signInPwCheckMsgTv.visibility = View.INVISIBLE
                passwordValidity = true
            }
        }
    }

    private fun setLoginDB() {
        if (emailValidity && passwordValidity) {
            binding.signInEmailCheckMsgTv.visibility = View.INVISIBLE
            binding.signInPwCheckMsgTv.visibility = View.INVISIBLE
            val email: String =
                binding.signInEmailTv.text.toString() + "@" + binding.signInDomainTv.text.toString()
            val pw: String = binding.signInPasswordTv.text.toString()

            val authService = AuthService()
            authService.setLoginView(this)
            authService.login(User(email, pw, ""))

//            val songDB = SongDataBase.getInstance(this)!!
//            val user = songDB.userDao().getUser(email, pw)
//
//            val users = songDB.userDao().getUsers()
//            Log.d("유저정보", users.toString())
//
//            when (user == null) {
//                true -> Toast.makeText(this, "회원정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
//                else -> {
//                    user.let {
//                        Log.d("LOGIN", "userId: ${user.id}, $user")
//                        saveJwt(user.id)
//                        startActivity(Intent(this, MainActivity::class.java))
//                    }
//                }
//            }
        }
    }

    override fun onLoginLoading() {
        binding.signInLoadingPb.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(auth: Auth) {
        binding.signInLoadingPb.visibility = View.GONE
        SharedPreferenceController.saveJwt(this@SignInActivity, auth.jwt)
        SharedPreferenceController.saveUserIdx(this@SignInActivity, auth.userIdx)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.signInLoadingPb.visibility = View.GONE
        when (code) {
            2015, 2019, 3014 -> {
                binding.signInEmailCheckMsgTv.visibility = View.VISIBLE
                binding.signInEmailCheckMsgTv.text = message
            }
        }
    }
}
