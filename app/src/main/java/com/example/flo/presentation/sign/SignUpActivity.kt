package com.example.flo.presentation.sign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignUpBinding
import com.example.flo.presentation.util.KeyBoardUtil

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackBtnClickListener()
    }

    private fun setBackBtnClickListener() {
        binding.signUpBtnBackIb.setOnClickListener {
            finish()
            KeyBoardUtil.hide(this)
        }
    }
}
