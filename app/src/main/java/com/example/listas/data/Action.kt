package com.example.listas.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(val category: Int, val priority: Int, val description: String) : Parcelable