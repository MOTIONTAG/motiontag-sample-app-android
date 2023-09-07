package de.motiontag.sampleapp.onboarding.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.motiontag.sampleapp.databinding.FOnboardingLocationPermissionBinding
import de.motiontag.sampleapp.injections.Dagger
import de.motiontag.sampleapp.onboarding.OnboardingViewModel
import javax.inject.Inject


class OnboardingLocationPermissionFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FOnboardingLocationPermissionBinding
    private val viewModel: OnboardingViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[OnboardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FOnboardingLocationPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Dagger.getComponent().inject(this)
        binding.locationPermissionButton.setText(viewModel.locationPermissionButtonText)
        binding.locationPermissionButton.setOnClickListener {
            viewModel.checkLocationPermissions(requireActivity())
        }
        binding.skipLocationPermissionButton.setOnClickListener {
            viewModel.nextPage()
        }
    }

}
