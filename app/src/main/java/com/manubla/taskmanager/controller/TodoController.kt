package com.manubla.taskmanager.controller

import com.manubla.taskmanager.service.TodoService
import com.manubla.taskmanager.service.request.TodoRequest
import com.manubla.taskmanager.service.response.TodoResponse
import org.threeten.bp.ZonedDateTime

class TodoController {

    private val todoService = RetrofitController.retrofit.create(TodoService::class.java)

    suspend fun getTodos(): List<TodoResponse> {
       return todoService.getTodos()
    }

    suspend fun createTodo(priority: String, description: String,
                           dueDate: ZonedDateTime, categoryId: Int): TodoResponse {
        val request = TodoRequest(priority, description, dueDate, false, categoryId)
        return todoService.createTodo(request)
    }

    suspend fun modifyTodo(id: Int, priority: String, description: String,
                           dueDate: ZonedDateTime, completed: Boolean, categoryId: Int): TodoResponse {
        val request = TodoRequest(priority, description, dueDate, completed, categoryId)
        return todoService.modifyTodo(id, request)
    }

    suspend fun removeTodo(id : Int) {
        todoService.removeTodo(id)
    }

}
