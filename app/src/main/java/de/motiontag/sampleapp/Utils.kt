package de.motiontag.sampleapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

private const val TRACKING_CHANNEL_ID = "tracking_channel"

fun Long.toDateTime(): String {
    val formatter = SimpleDateFormat.getDateTimeInstance()
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun Context.getForegroundNotification(): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel()
    }
    val notification = NotificationCompat.Builder(this, TRACKING_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification_icon)
        .setContentTitle(this.getString(R.string.app_name))
        .setContentText(this.getString(R.string.tracking_active))
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()
    notification.flags = Notification.FLAG_ONGOING_EVENT
    return notification
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Context.createNotificationChannel() {
    val notificationService =
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val title = this.getString(R.string.notification_channel_title)
    val description = this.getString(R.string.notification_channel_description)
    val notificationChannel =
        NotificationChannel(TRACKING_CHANNEL_ID, title, NotificationManager.IMPORTANCE_LOW)
    notificationChannel.description = description
    notificationChannel.setShowBadge(false)
    notificationService.createNotificationChannel(notificationChannel)
}
