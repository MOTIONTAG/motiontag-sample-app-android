package de.motiontag.sampleapp.onboarding.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.motiontag.sampleapp.databinding.FOnboardingLocationSettingsBinding
import de.motiontag.sampleapp.injections.Dagger
import de.motiontag.sampleapp.onboarding.OnboardingViewModel
import javax.inject.Inject


class OnboardingLocationSettingsFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FOnboardingLocationSettingsBinding
    private val viewModel: OnboardingViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[OnboardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FOnboardingLocationSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Dagger.getComponent().inject(this)
        binding.locationSettingsButton.setText(viewModel.locationSettingsButtonText)
        binding.locationSettingsButton.setOnClickListener {
            viewModel.checkLocationSettings(requireActivity())
        }
        binding.skipLocationSettingsButton.setOnClickListener {
            viewModel.nextPage()
        }
    }
}
