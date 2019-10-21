package com.manubla.taskmanager.controller

import com.manubla.taskmanager.service.UserService
import com.manubla.taskmanager.service.response.UserResponse

class UserController {
    private val authService = RetrofitController.retrofit.create(UserService::class.java)

    suspend fun getUserInfo(): UserResponse {
        return authService.getUserInfo()

    }

}
