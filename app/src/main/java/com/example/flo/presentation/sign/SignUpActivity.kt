package com.example.flo.presentation.sign

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.data.User
import com.example.flo.data.api.AuthService
import com.example.flo.data.api.view.SignUpView
import com.example.flo.databinding.ActivitySignUpBinding
import com.example.flo.presentation.util.KeyBoardUtil

class SignUpActivity : AppCompatActivity(), SignUpView {
    private lateinit var binding: ActivitySignUpBinding
    private var emailValidity: Boolean = false
    private var passwordValidity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackBtnClickListener()
        setSignUpBtnClickListener()
    }

    private fun setBackBtnClickListener() {
        binding.signUpBtnBackIb.setOnClickListener {
            finish()
            KeyBoardUtil.hide(this)
        }
    }

    private fun getUser(): User {
        val email: String =
            binding.signUpEmailTv.text.toString() + "@" + binding.signUpDomainTv.text.toString()
        val password: String = binding.signUpPasswordTv.text.toString()
        val name: String = binding.signUpNameTv.text.toString()

        return User(email, password, name)
    }

    private fun setSignUpBtnClickListener() {
        binding.signUpBtnComplete.setOnClickListener {
            setSignUp()
        }
    }

    private fun setSignUp() {
        with(binding) {
            when (signUpEmailTv.text.isNullOrEmpty() || signUpDomainTv.text.isNullOrEmpty()) {
                true -> {
                    signUpEmailTv.setBackgroundResource(R.drawable.border_red_under_line)
                    signUpEmailCheckMsgTv.visibility = View.VISIBLE
                    emailValidity = false
                }
                else -> {
                    signUpEmailTv.setBackgroundResource(R.drawable.sign_et_bg_selector)
                    signUpEmailCheckMsgTv.visibility = View.INVISIBLE
                    emailValidity = true
                }
            }

            when (signUpPasswordTv.text.toString() != signUpPasswordCheckTv.text.toString()) {
                true -> {
                    signUpPasswordCheckTv.setBackgroundResource(R.drawable.border_red_under_line)
                    signUpPasswordUncheckMsgTv.visibility = View.VISIBLE
                    signUpPasswordCheckMsgTv.visibility = View.INVISIBLE
                    passwordValidity = false
                }
                else -> {
                    signUpPasswordCheckTv.setBackgroundResource(R.drawable.sign_et_bg_selector)
                    signUpPasswordCheckMsgTv.visibility = View.VISIBLE
                    signUpPasswordUncheckMsgTv.visibility = View.INVISIBLE
                    passwordValidity = true
                }
            }
        }

        if (passwordValidity && emailValidity) {
            val authService = AuthService()
            authService.setSignUpView(this)
            authService.signUp(getUser())
        }
    }

    override fun onSignUpLoading() {
        binding.signUpLoadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.signUpLoadingPb.visibility = View.GONE
        finish()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.signUpLoadingPb.visibility = View.GONE
        when (code) {
            2016, 2017 -> {
                binding.signUpEmailCheckMsgTv.visibility = View.VISIBLE
                binding.signUpEmailCheckMsgTv.text = message
            }
        }
    }
}
