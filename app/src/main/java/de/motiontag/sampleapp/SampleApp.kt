package de.motiontag.sampleapp

import android.app.Application
import de.motiontag.sampleapp.utils.getForegroundNotification
import de.motiontag.tracker.MotionTag
import de.motiontag.tracker.Settings
import de.motiontag.tracker.models.Event

const val CHANNEL_ID = "channel_id"

class SampleApp : Application(), MotionTag.Callback {

    override fun onCreate() {
        super.onCreate()

        val notification = getForegroundNotification()
        val settings = Settings.Builder().notification(notification).build()
        MotionTag.with(this, settings, this)
    }

    override fun onEvent(event: Event) {
        // TODO: Handle SDK events
    }
}
