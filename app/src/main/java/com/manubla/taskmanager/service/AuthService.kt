package com.manubla.taskmanager.service

import com.manubla.taskmanager.service.request.LoginRequest
import com.manubla.taskmanager.service.request.SignupRequest
import com.manubla.taskmanager.service.response.SuccessReponse
import com.manubla.taskmanager.service.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("auth/logout")
    suspend fun logout(): SuccessReponse

    @POST("auth/signup")
    suspend fun signup(@Body loginRequest: SignupRequest): TokenResponse
}
