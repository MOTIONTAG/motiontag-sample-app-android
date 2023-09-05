package de.motiontag.sampleapp.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(
    private val onboardingPages: List<OnboardingPage>,
    lifecycle: Lifecycle,
    fragmentManager: FragmentManager
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = onboardingPages.size

    override fun createFragment(position: Int): Fragment = onboardingPages[position].build()
}
