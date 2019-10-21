package com.manubla.taskmanager.service.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResponse(
    val id: Int,
    val name: String,
    val color: String,
    val photo: String
): Parcelable
