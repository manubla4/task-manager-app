package com.manubla.taskmanager.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
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

fun nativeCameraRequest(fragment: Fragment) {
    fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
}