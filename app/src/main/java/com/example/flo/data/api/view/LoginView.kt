package com.example.flo.data.api.view

import com.example.flo.data.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}