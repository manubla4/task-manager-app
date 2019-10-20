package com.manubla.taskmanager.service

import com.manubla.taskmanager.service.response.TodosReponse
import retrofit2.http.GET

interface TodoService {
    @GET("todos")
    suspend fun getTodos(): TodosReponse

}
