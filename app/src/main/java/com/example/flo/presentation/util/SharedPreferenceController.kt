package com.example.flo.presentation.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferenceController {

    fun getUserIdx(context: Context): Int {
        val sdf = context.getSharedPreferences("auth", MODE_PRIVATE)
        return sdf!!.getInt("userIdx", 0)
    }

    fun getJwt(context: Context): String? {
        val sdf = context.getSharedPreferences("auth", MODE_PRIVATE)
        return sdf!!.getString("jwt", "0")
    }

    fun saveJwt(context: Context, jwt: String) {
        val sdf = context.getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sdf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    fun saveUserIdx(context: Context, userIdx: Int) {
        val sdf = context.getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sdf.edit()

        editor.putInt("userIdx", userIdx)
        editor.apply()
    }
}