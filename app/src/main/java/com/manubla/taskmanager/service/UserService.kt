package com.manubla.taskmanager.service

import com.manubla.taskmanager.service.response.UserResponse
import retrofit2.http.GET

interface UserService {
    @GET("users/me")
    suspend fun getUserInfo(): UserResponse
}