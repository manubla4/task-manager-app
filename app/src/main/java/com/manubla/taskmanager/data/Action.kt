package com.manubla.taskmanager.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(val priority: Priority, val category: Category, val description: String) : Parcelable

enum class Priority(val description: String) {
    HIGH("High Priority"),
    MEDIUM("Medium Priority"),
    LOW("Low Priority")
}

enum class Category {
    WORK,
    STUDY,
    SHOPPING,
    LEISURE
}
