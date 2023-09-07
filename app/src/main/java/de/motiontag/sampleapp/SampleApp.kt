package de.motiontag.sampleapp

import android.app.Application
import android.util.Log
import de.motiontag.sampleapp.injections.Dagger
import de.motiontag.sampleapp.utils.getForegroundNotification
import de.motiontag.tracker.*

private const val LOG_TAG = "SampleApp"

class SampleApp : Application(), MotionTag.Callback {

    override fun onCreate() {
        super.onCreate()
        Dagger.init(this)
        Dagger.getComponent().inject(this)
        val notification = getForegroundNotification()
        val motionTag = MotionTag.getInstance()
        motionTag.initialize(this, notification, this)
        motionTag.wifiOnlyDataTransfer = false
    }

    override fun onEvent(event: Event) {
        when (event) {
            is AutoStartEvent -> Log.d(LOG_TAG, "AutoStart: $event")
            is AutoStopEvent -> Log.d(LOG_TAG, "AutoStop: $event")
            is LocationEvent -> Log.d(LOG_TAG, "Location: $event")
            is TransmissionEvent.Success -> Log.d(LOG_TAG, "Transmission Success: $event")
            is TransmissionEvent.Error -> Log.d(LOG_TAG, "Transmission Error: $event")
            is BatteryOptimizationsChangedEvent -> Log.d(LOG_TAG, "Battery Optimizations Changed: $event")
            is PowerSaveModeChangedEvent -> Log.d(LOG_TAG, "Power Save Mode Changed: $event")
        }
    }
}
