package com.manubla.taskmanager.controller

import com.manubla.taskmanager.service.TodoService
import com.manubla.taskmanager.service.response.TodosReponse

class TodoController {
    private val todoService = RetrofitController.retrofit.create(TodoService::class.java)

    suspend fun getTodos(): TodosReponse {
       return todoService.getTodos()
    }

}
