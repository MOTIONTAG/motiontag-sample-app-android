package de.motiontag.sampleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import de.motiontag.sampleapp.utils.containsDeniedResult
import de.motiontag.sampleapp.utils.requestPermissionsOrShowRationale
import de.motiontag.sampleapp.utils.showPermissionsDeniedDialog
import de.motiontag.tracker.MotionTag
import kotlinx.android.synthetic.main.activity_main.*

private const val USER_TOKEN = "User's JWT token"
private const val PERMISSIONS_REQUEST_CODE = 100
private const val SETTINGS_REQUEST_CODE = 200

class MainActivity : AppCompatActivity() {

    private val motionTag: MotionTag by lazy { MotionTag.getInstance() }

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
        if (requestCode != PERMISSIONS_REQUEST_CODE) return

        if (containsDeniedResult(grantResults)) {
            showPermissionsDeniedDialog(this)
        } else {
            checkSettingsAndPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != SETTINGS_REQUEST_CODE) return

        if (resultCode == Activity.RESULT_OK) {
            checkSettingsAndPermissions()
        }
    }

    private fun setTrackingListener() {
        trackingButton.setOnClickListener {
            if (motionTag.isTrackingActive()) {
                stopTracking()
            } else {
                checkSettingsAndPermissions()
            }
        }
    }

    private fun checkSettingsAndPermissions() {
        if (!motionTag.hasRequiredLocationSettings()) {
            requestLocationSettings()
        } else if (!motionTag.hasRequiredPermissions()) {
            requestPermissionsOrShowRationale(this, PERMISSIONS_REQUEST_CODE)
        } else {
            startTracking()
        }
    }

    private fun requestLocationSettings() {
        motionTag.requestRequiredLocationSettings(this, SETTINGS_REQUEST_CODE)
    }

    private fun stopTracking() {
        motionTag.stop()
        updateTrackingButton()
    }

    private fun startTracking() {
        motionTag.userToken(USER_TOKEN)
        motionTag.start()
        updateTrackingButton()
    }

    private fun updateTrackingButton() {
        if (motionTag.isTrackingActive()) {
            trackingButton.text = getString(R.string.stop_tracking)
            trackingButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        } else {
            trackingButton.text = getString(R.string.start_tracking)
            trackingButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        }
    }
}
