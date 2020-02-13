package de.motiontag.sampleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import de.motiontag.tracker.MotionTag
import kotlinx.android.synthetic.main.activity_main.startTrackingButton
import kotlinx.android.synthetic.main.activity_main.stopTrackingButton

private const val JWT_TOKEN = "The JWT token oes here"
private const val PERMISSIONS_REQUEST_CODE = 100
private const val SETTINGS_REQUEST_CODE = 200

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtonOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updateButtons()
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

    private fun initButtonOnClickListeners() {
        startTrackingButton.setOnClickListener {
            if (!MotionTag.hasRequiredLocationSettings()) {
                MotionTag.requestRequiredLocationSettings(this, SETTINGS_REQUEST_CODE)
            } else if (!MotionTag.hasRequiredPermissions()) {
                MotionTag.requestRequiredPermissions(this, PERMISSIONS_REQUEST_CODE)
            } else {
                startTracking()
            }
        }
        stopTrackingButton.setOnClickListener {
            stopTracking()
        }
    }

    private fun startTracking() {
        MotionTag.start(JWT_TOKEN)
        updateButtons()
    }

    private fun stopTracking() {
        MotionTag.stop()
        updateButtons()
    }

    private fun updateButtons() {
        val isActive = MotionTag.isTrackingActive()
        startTrackingButton.isVisible = !isActive
        stopTrackingButton.isVisible = isActive
    }
}
