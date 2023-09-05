package de.motiontag.sampleapp.onboarding

import androidx.fragment.app.Fragment
import de.motiontag.sampleapp.onboarding.pages.OnboardingLocationPermissionFragment
import de.motiontag.sampleapp.onboarding.pages.OnboardingLocationSettingsFragment


sealed class OnboardingPage {
    abstract fun build(): Fragment

    object LocationSettings : OnboardingPage() {
        override fun build(): Fragment = OnboardingLocationSettingsFragment()
    }

    object LocationPermission : OnboardingPage() {
        override fun build(): Fragment = OnboardingLocationPermissionFragment()
    }

}
