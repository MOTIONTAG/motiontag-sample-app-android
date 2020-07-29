package de.motiontag.sampleapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.motiontag.sampleapp.R

private const val DIALOG_STYLE: Int = R.style.ThemeOverlay_MaterialComponents_Dialog_Alert

fun showPermissionsRationaleDialog(
    context: Context,
    permissions: List<String>,
    positiveAction: () -> (Unit)
) {
    MaterialAlertDialogBuilder(context, DIALOG_STYLE)
        .setTitle(R.string.permissions_required_dialog_title)
        .setMessage(context.getString(R.string.permissions_required_dialog_message, permissions))
        .setPositiveButton(android.R.string.ok) { _, _ -> positiveAction.invoke() }
        .setNegativeButton(android.R.string.cancel, null)
        .create()
        .show()
}

fun showPermissionsDeniedDialog(activity: Activity) {
    MaterialAlertDialogBuilder(activity, DIALOG_STYLE)
        .setTitle(R.string.permissions_denied_dialog_title)
        .setMessage(R.string.permissions_denied_dialog_message)
        .setPositiveButton(R.string.go_to_settings) { _, _ -> openAppSettings(activity) }
        .setNegativeButton(android.R.string.cancel, null)
        .create()
        .show()
}

private fun openAppSettings(activity: Activity) {
    val scheme = "package"
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts(scheme, activity.packageName, null)
    intent.data = uri
    activity.startActivity(intent)
}
