package com.manubla.taskmanager.extension

import android.view.View
import android.widget.EditText

fun EditText.textString() = text.toString()

fun EditText.editableTextString() = editableText.toString()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleIf(condition: Boolean) {
    if (condition) visible() else gone()
}