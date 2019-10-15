package com.manubla.taskmanager.utils

import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

private const val LENGTH_SHORT = 0
private const val LENGTH_LONG = 1

private const val STATE_NORMAL = 0
private const val STATE_SUCCESS = 1
private const val STATE_ERROR = 2

private var mSnackbar: WeakReference<Snackbar> = WeakReference<Snackbar>(null)
private var mDialog: WeakReference<AlertDialog> = WeakReference<AlertDialog>(null)
