package com.manubla.taskmanager.service.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime

@Parcelize
data class TodoRequest(
    val priority: String,
    val description: String,
    val due_date: ZonedDateTime,
    val completed: Boolean,
    val category_id: Int
) : Parcelable
