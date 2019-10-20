package com.manubla.taskmanager.service

import com.diegomedina.notesapp.service.request.LoginRequest
import com.diegomedina.notesapp.service.response.SuccessReponse
import com.diegomedina.notesapp.service.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("auth/logout")
    suspend fun logout(): SuccessReponse
}
