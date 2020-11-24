package de.motiontag.sampleapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import de.motiontag.sampleapp.MainActivity
import de.motiontag.sampleapp.R

private const val TRACKING_CHANNEL_ID = "tracking_channel"

fun Context.getForegroundNotification(): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel()
    }
    val notification = NotificationCompat.Builder(this, TRACKING_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification_icon)
        .setContentTitle(this.getString(R.string.app_name))
        .setContentText(this.getString(R.string.tracking_active))
        // Starting from SDK version 5.0.0, it is recommended to use a low priority notification
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setContentIntent(this.getContentIntent())
        .build()
    notification.flags = Notification.FLAG_ONGOING_EVENT
    return notification
}

private fun Context.getContentIntent(): PendingIntent {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Context.createNotificationChannel() {
    val notificationService =
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val title = this.getString(R.string.notification_channel_title)
    val description = this.getString(R.string.notification_channel_description)
    // Starting from SDK version 5.0.0, it is recommended to use a low importance channel
    val notificationChannel =
        NotificationChannel(TRACKING_CHANNEL_ID, title, NotificationManager.IMPORTANCE_LOW)
    notificationChannel.description = description
    notificationChannel.setShowBadge(false)
    notificationService.createNotificationChannel(notificationChannel)
}
