package com.manubla.taskmanager.service.request

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)
