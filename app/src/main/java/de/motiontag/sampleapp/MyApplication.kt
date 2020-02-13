package de.motiontag.sampleapp

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import de.motiontag.tracker.MotionTag
import de.motiontag.tracker.Settings
import de.motiontag.tracker.models.Event

const val MY_CHANNEL_ID = "my_channel_id"

class MyApplication : Application(), MotionTag.Callback {
    override fun onCreate() {
        super.onCreate()
        val notification = getForegroundNotification(this)
        val settings = Settings.Builder().notification(notification).build()
        MotionTag.with(this, settings, this)
    }

    override fun onEvent(event: Event) {
        // TODO: Handle SDK events
    }

    private fun getForegroundNotification(context: Context): Notification {
        initNotificationChannel(context)

        val notification = NotificationCompat.Builder(context, MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.tracking_activated_title))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        notification.flags = Notification.FLAG_ONGOING_EVENT
        return notification
    }

    private fun initNotificationChannel(context: Context) {
        val notificationService =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = notificationService.getNotificationChannel(MY_CHANNEL_ID)
            if (notificationChannel == null) {
                val newNotificationChannel = NotificationChannel(
                    MY_CHANNEL_ID, context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW
                )
                newNotificationChannel.setShowBadge(false)
                notificationService.createNotificationChannel(newNotificationChannel)
            }
        }
    }
}
