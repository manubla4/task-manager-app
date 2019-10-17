package com.manubla.taskmanager.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


val REQUEST_CAMERA_PERMISSION = 0

fun isCameraPermissionGranted(activity: Activity): Boolean {
    return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
}

fun haveToExplainCameraPermission(activity: Activity): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)
}

fun nativeCameraRequest(activity: Activity) {
    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
}

fun nativeCameraRequest(fragment: Fragment) {
    fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
}

fun storeData(context: Context, key: String, value: Boolean) {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPref.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun retrieveData(context: Context, key: String, defaultValue: Boolean): Boolean {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPref.getBoolean(key, defaultValue)
}

fun storeData(context: Context, key: String, value: String) {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPref.edit()
    editor.putString(key, value)
    editor.apply()
}

fun retrieveData(context: Context, key: String, defaultValue: String): String? {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPref.getString(key, defaultValue)
}