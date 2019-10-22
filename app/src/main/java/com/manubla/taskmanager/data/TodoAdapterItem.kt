package com.manubla.taskmanager.data

data class TodoAdapterItem(
    val viewType: Int,
    val idTodo: Int,
    val description: String,
    val priority: String,
    val categoryColor: String,
    val completed: Boolean,
    val dueDayOfWeek: String,
    val dueDate: String
)

