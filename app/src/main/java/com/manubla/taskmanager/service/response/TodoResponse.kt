package com.manubla.taskmanager.service.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime

@Parcelize
data class TodoResponse(
    val id: Int,
    val description: String,
    val completed: Boolean,
    val due_date: ZonedDateTime,
    val priority: String,
    val category_id: Int,
    val category: CategoryResponse
) : Parcelable
