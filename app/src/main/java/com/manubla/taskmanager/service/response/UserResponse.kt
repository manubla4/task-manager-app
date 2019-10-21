package com.manubla.taskmanager.service.response

import org.threeten.bp.ZonedDateTime

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val created_at: ZonedDateTime
)
