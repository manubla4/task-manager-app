package com.manubla.taskmanager.service

import com.manubla.taskmanager.service.request.TodoRequest
import com.manubla.taskmanager.service.response.TodoResponse
import retrofit2.http.*

interface TodoService {
    @GET("todos")
    suspend fun getTodos(): List<TodoResponse>

    @POST("todos")
    suspend fun createTodo(@Body request: TodoRequest): TodoResponse

    @PUT("todos/{id}")
    suspend fun modifyTodo(@Path("id") id: Int, @Body request: TodoRequest): TodoResponse

    @DELETE("todos/{id}")
    suspend fun removeTodo(@Path("id") id: Int): Void
}
