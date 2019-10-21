package com.manubla.taskmanager.service

import com.manubla.taskmanager.service.response.CategoryResponse
import com.manubla.taskmanager.service.response.TodoResponse
import retrofit2.http.GET

interface CategoryService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryResponse>

}
