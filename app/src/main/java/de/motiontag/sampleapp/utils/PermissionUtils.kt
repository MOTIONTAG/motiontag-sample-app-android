package de.motiontag.sampleapp.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import de.motiontag.tracker.MotionTag

fun requestPermissionsOrShowRationale(activity: Activity, requestCode: Int) {
    val permissionsToRationale = activity.getPermissionsToRationale()
    if (permissionsToRationale.isEmpty()) {
        requestPermissions(activity, activity.getPermissions(), requestCode)
    } else {
        showPermissionsRationaleDialog(activity, permissionsToRationale) {
            requestPermissions(activity, activity.getPermissions(), requestCode)
        }
    }
}

fun hasPermission(activity: Activity, permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        activity,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun containsDeniedResult(grantResults: IntArray): Boolean {
    return grantResults.contains(PackageManager.PERMISSION_DENIED)
}

private fun Activity.getPermissionsToRationale(): List<String> {
    val permissions = getPermissions()
    return permissions.filter { permission ->
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    }
}

private fun Activity.getPermissions(): List<String> {
    val permissions = MotionTag.getInstance().deniedRequiredPermissions
    return if (isAllowedToRequestBackgroundLocation()) {
        permissions
    } else {
        permissions.minus(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }
}

private fun requestPermissions(activity: Activity, permissions: List<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
}

// ACCESS_FINE_LOCATION permission must be granted before requesting ACCESS_BACKGROUND_LOCATION when targetSdkVersion >= 30
// https://developer.android.com/preview/privacy/location#background-location
private fun Activity.isAllowedToRequestBackgroundLocation(): Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.R ||
            hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
}
