package de.motiontag.sampleapp

import android.app.Application
import android.util.Log
import de.motiontag.sampleapp.utils.getForegroundNotification
import de.motiontag.tracker.*

private const val LOG_TAG = "SampleApp"

class SampleApp : Application(), MotionTag.Callback {

    override fun onCreate() {
        super.onCreate()

        val notification = getForegroundNotification()
        val settings = Settings.Builder()
            .notification(notification)
            .useBatterySavingMode(true)
            .useWifiOnlyDataTransfer(true)
            .build()
        MotionTag.with(this, settings, this)
    }

    override fun onEvent(event: Event) {
        when (event) {
            is AutoStartEvent -> Log.d(LOG_TAG, "AutoStart: $event")
            is AutoStopEvent -> Log.d(LOG_TAG, "AutoStop: $event")
            is LocationEvent -> Log.d(LOG_TAG, "Location: $event")
            is TransmissionEvent -> {
                when (event) {
                    is TransmissionEvent.Success -> Log.d(LOG_TAG, "Transmission Success: $event")
                    is TransmissionEvent.Error -> Log.d(LOG_TAG, "Transmission Error: $event")
                }
            }
        }
    }
}
