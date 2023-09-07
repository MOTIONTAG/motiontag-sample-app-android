package de.motiontag.sampleapp.injections.components

import de.motiontag.sampleapp.MainActivity
import de.motiontag.sampleapp.SampleApp
import de.motiontag.sampleapp.onboarding.OnboardingActivity
import de.motiontag.sampleapp.onboarding.pages.OnboardingLocationPermissionFragment
import de.motiontag.sampleapp.onboarding.pages.OnboardingLocationSettingsFragment

interface AppComponent {
    fun inject(application: SampleApp)
    fun inject(mainActivity: MainActivity)
    fun inject(onboardingActivity: OnboardingActivity)
    fun inject(onboardingLocationSettingsFragment: OnboardingLocationSettingsFragment)
    fun inject(onboardingLocationPermissionFragment: OnboardingLocationPermissionFragment)
}
