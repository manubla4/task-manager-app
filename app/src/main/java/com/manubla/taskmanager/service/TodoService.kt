package com.manubla.taskmanager.service

import com.manubla.taskmanager.service.request.TodoRequest
import com.manubla.taskmanager.service.response.TodoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoService {
    @GET("todos")
    suspend fun getTodos(): List<TodoResponse>

    @POST("todos")
    suspend fun createTodo(@Body request: TodoRequest): TodoResponse
}
