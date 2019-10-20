package com.manubla.taskmanager.util

import android.app.Activity
import android.graphics.Color
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.manubla.taskmanager.R
import java.lang.ref.WeakReference

private const val LENGTH_SHORT = 0
private const val LENGTH_LONG = 1

private const val STATE_NORMAL = 0
private const val STATE_SUCCESS = 1
private const val STATE_ERROR = 2

private var mSnackbar: WeakReference<Snackbar> = WeakReference<Snackbar>(null)
private var mDialog: WeakReference<AlertDialog> = WeakReference<AlertDialog>(null)


private fun showMessage(message: String, activity: Activity?, length: Int, state: Int) {
    val snackbarPrev = mSnackbar.get()
    if (snackbarPrev != null && snackbarPrev.isShown)
        snackbarPrev.dismiss()

    if (activity != null) {
        val view = activity.window.decorView.rootView
        val snackbar = Snackbar.make(view, message,
            (if (length == LENGTH_SHORT) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG))
        snackbar.apply {
            setAction("OK") { dismiss() }
            if (state == STATE_SUCCESS) {
                view.setBackgroundColor(ContextCompat.getColor(activity,R.color.colorSuccess))
                setActionTextColor(Color.WHITE)
            }
            if (state == STATE_ERROR) {
                view.setBackgroundColor(ContextCompat.getColor(activity,R.color.colorFailure))
                setActionTextColor(Color.WHITE)
            }
            show()
            mSnackbar = WeakReference(this)
        }
    }
}


fun showLongMessage(message: String, activity: Activity?) {
    showMessage(message, activity, LENGTH_LONG, STATE_NORMAL)
}

fun showShortMessage(message: String, activity: Activity?) {
    showMessage(message, activity, LENGTH_SHORT, STATE_NORMAL)
}

fun showLongSuccessMessage(message: String, activity: Activity?) {
    showMessage(message, activity, LENGTH_LONG, STATE_SUCCESS)
}

fun showShortSuccessMessage(message: String, activity: Activity?) {
    showMessage(message, activity, LENGTH_SHORT, STATE_SUCCESS)
}

fun showLongErrorMessage(message: String, activity: Activity?) {
    showMessage(message, activity, LENGTH_LONG, STATE_ERROR)
}

fun showShortErrorMessage(message: String, activity: Activity?) {
    showMessage(message, activity, LENGTH_SHORT, STATE_ERROR)
}


fun showPermissionDialog(fragment: Fragment, activity: Activity, title: String, message: String, requestCode: Int) {
    val callBack = fragment as CallBackListener
    val builder = AlertDialog.Builder(activity, R.style.AppTheme_AlertDialog)
    builder.setMessage(message)
        .setTitle(title)
        .setPositiveButton("Reintentar") { dialog, _ ->
            dialog.dismiss()
            callBack.onAcceptDialog(requestCode)
        }
        .setNegativeButton("Rechazar") { dialog, _ ->
            dialog.dismiss()
            callBack.onRejectDialog(requestCode)
        }
        .setOnKeyListener { dialog, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss()
                callBack.onRejectDialog(requestCode)
            }
            true
        }
    mDialog.get().apply {
        if (this != null && isShowing) {
            dismiss()
            cancel()
        }
    }
    builder.create().apply {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        show()
        mDialog = WeakReference(this)
    }
}


fun showAlertOptionsDialog(fragment: Fragment, activity: Activity, title: String,
                           message: String, requestCode: Int) {
    val callBack = fragment as CallBackListener
    val builder = AlertDialog.Builder(activity, R.style.AppTheme_AlertDialog)
    builder.setMessage(message)
        .setTitle(title)
        .setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
            callBack.onAcceptDialog(requestCode)
        }
        .setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
            callBack.onRejectDialog(requestCode)
        }
    mDialog.get().apply {
        if (this != null && isShowing) {
            dismiss()
            cancel()
        }
    }
    builder.create().apply {
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        show()
        mDialog = WeakReference(this)
    }
}


interface CallBackListener {
    fun onAcceptDialog(requestCode: Int)
    fun onRejectDialog(requestCode: Int)
}