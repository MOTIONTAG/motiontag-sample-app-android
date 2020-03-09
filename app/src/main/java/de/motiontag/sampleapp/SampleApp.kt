package de.motiontag.sampleapp

import android.app.Application
import android.util.Log
import de.motiontag.tracker.MotionTag
import de.motiontag.tracker.Settings
import de.motiontag.tracker.models.Event

class SampleApp : Application(), MotionTag.Callback {

    override fun onCreate() {
        super.onCreate()

        val notification = getForegroundNotification()
        val settings = Settings.Builder()
            .notification(notification)
            .useBatterySavingMode(true)
            .useWifiOnlyDataTransfer(true)
            .turnLoggingOn(true)
            .build()
        MotionTag.with(this, settings, this)
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.AutoStart -> Log.d("SampleApp", "AutoStart Event: ${event.reason}")
            is Event.AutoStop -> Log.d("SampleApp", "AutoStop Event: ${event.reason}")
            is Event.Location -> Log.d("SampleApp", "Location Event: ${event.location}")
            is Event.Transmission -> Log.d(
                "SampleApp", "Transmission Event: ${event.transmittedAt.toDateTime()}"
            )
        }
    }
}
