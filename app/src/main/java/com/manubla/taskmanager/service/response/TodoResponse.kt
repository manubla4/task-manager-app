package com.manubla.taskmanager.service.response

data class TodoResponse(
    val description: String,
    val completed: Boolean,
    val due_date: String,
    val priority: String,
    val category_id: Int,
    val category: CategoryReponse
)
