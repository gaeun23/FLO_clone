package com.example.flo.presentation.sign

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignInBinding
import com.example.flo.presentation.util.KeyBoardUtil

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCloseBtnClickListener()
        setSignUpBtnClickListener()
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
}
