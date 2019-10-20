package com.manubla.taskmanager.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(val actionType: Int, val description: String) : Parcelable

