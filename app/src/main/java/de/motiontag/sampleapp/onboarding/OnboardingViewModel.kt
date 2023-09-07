package de.motiontag.sampleapp.onboarding

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.motiontag.sampleapp.PERMISSIONS_REQUEST_CODE
import de.motiontag.sampleapp.R
import de.motiontag.sampleapp.SETTINGS_REQUEST_CODE
import de.motiontag.sampleapp.utils.requestPermissionsOrShowRationale
import de.motiontag.tracker.MotionTag
import javax.inject.Inject


class OnboardingViewModel @Inject constructor(): ViewModel() {

    var currentPageIndex: MutableLiveData<Int> = MutableLiveData(0)
        private set
    val onboardingPages: List<OnboardingPage> = buildOnboardingPages()
    val motionTag = MotionTag.getInstance()
    val locationPermissionButtonText: Int get() = hasLocationPermissions.buildEnableButtonText()
    val locationSettingsButtonText: Int get() = hasLocationSettings.buildEnableButtonText()
    private val hasLocationSettings: Boolean get() = motionTag.hasRequiredLocationSettings
    private val hasLocationPermissions: Boolean get() = motionTag.hasRequiredPermissions


    fun nextPage() {
        var index = currentPageIndex.value ?: return
        index++
        currentPageIndex.value = index
    }

    fun checkLocationSettings(activity: Activity) {
        if (hasLocationSettings) nextPage()
        else motionTag.requestRequiredLocationSettings(activity, SETTINGS_REQUEST_CODE)
    }

    fun checkLocationPermissions(activity: Activity) {
        if (hasLocationPermissions) {
            nextPage()
        } else {
            requestPermissionsOrShowRationale(activity, PERMISSIONS_REQUEST_CODE)
        }
    }




    private fun buildOnboardingPages(): List<OnboardingPage> {
        val pages = mutableListOf<OnboardingPage>()

        pages.add(OnboardingPage.LocationSettings)
        pages.add(OnboardingPage.LocationPermission)

        return pages
    }


    private fun Boolean.buildEnableButtonText(): Int {
        return if (this) R.string.onboarding_ok_understood
        else R.string.onboarding_enable
    }

}
