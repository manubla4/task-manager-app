package com.manubla.taskmanager.controller

import com.manubla.taskmanager.service.request.LoginRequest
import com.manubla.taskmanager.App
import com.manubla.taskmanager.service.AuthService
import com.manubla.taskmanager.service.request.SignupRequest

class AuthController {
    private val authService = RetrofitController.retrofit.create(AuthService::class.java)

    suspend fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        val response = authService.login(request)

        with(response.authToken) {
            RetrofitController.accessToken = this
            SharedPreferencesController.saveToken(this, App.currentActivity.get())
        }
    }

    suspend fun signup(name: String, email: String, password: String, repassword: String) {
        val request = SignupRequest(name, email, password, repassword)
        val response = authService.signup(request)

        with(response.authToken) {
            RetrofitController.accessToken = this
            SharedPreferencesController.saveToken(this, App.currentActivity.get())
        }
    }

    suspend fun logout() {
        authService.logout()
        RetrofitController.accessToken = null
        SharedPreferencesController.removeToken(App.currentActivity.get())
    }
}
