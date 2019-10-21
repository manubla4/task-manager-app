package com.manubla.taskmanager.controller

import com.manubla.taskmanager.service.CategoryService
import com.manubla.taskmanager.service.TodoService
import com.manubla.taskmanager.service.response.CategoryResponse
import com.manubla.taskmanager.service.response.TodoResponse

class CategoryController {
    private val categoryService = RetrofitController.retrofit.create(CategoryService::class.java)

    suspend fun getCategories(): List<CategoryResponse> {
       return categoryService.getCategories()
    }

}
