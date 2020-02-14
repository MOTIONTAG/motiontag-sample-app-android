package de.motiontag.sampleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.motiontag.tracker.MotionTag
import kotlinx.android.synthetic.main.activity_main.trackingButton

private const val JWT_TOKEN = "The JWT token oes here"
private const val PERMISSIONS_REQUEST_CODE = 100
private const val SETTINGS_REQUEST_CODE = 200

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        setTrackingListener()
        updateTrackingButton()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && MotionTag.hasRequiredPermissions()) {
            startTracking()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            MotionTag.requestRequiredPermissions(this, PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun setTrackingListener() {
        trackingButton.setOnClickListener {
            if (MotionTag.isTrackingActive()) {
                stopTracking()
            } else {
                startTracking()
            }
        }
    }

    private fun startTracking() {
        if (!MotionTag.hasRequiredLocationSettings()) {
            MotionTag.requestRequiredLocationSettings(this, SETTINGS_REQUEST_CODE)
        } else if (!MotionTag.hasRequiredPermissions()) {
            MotionTag.requestRequiredPermissions(this, PERMISSIONS_REQUEST_CODE)
        } else {
            MotionTag.start(JWT_TOKEN)
            updateTrackingButton()
        }
    }

    private fun stopTracking() {
        MotionTag.stop()
        updateTrackingButton()
    }

    private fun updateTrackingButton() {
        if (MotionTag.isTrackingActive()) {
            trackingButton.text = getString(R.string.stop_tracking)
        } else {
            trackingButton.text = getString(R.string.start_tracking)
        }
    }
}
