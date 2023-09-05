package de.motiontag.sampleapp.onboarding

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import de.motiontag.sampleapp.MainActivity
import de.motiontag.sampleapp.PERMISSIONS_REQUEST_CODE
import de.motiontag.sampleapp.SETTINGS_REQUEST_CODE
import de.motiontag.sampleapp.databinding.OnboardingLayoutBinding
import de.motiontag.sampleapp.injections.Dagger
import javax.inject.Inject

class OnboardingActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: OnboardingLayoutBinding
    private val viewModel: OnboardingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[OnboardingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getComponent().inject(this)
        binding = OnboardingLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
        setTabLayoutMediator()
        observeViewModel()
    }

    private fun setViewPager() {
        val pageIndex = viewModel.currentPageIndex.value ?: 0
        val adapter =
            OnboardingPagerAdapter(viewModel.onboardingPages, lifecycle, supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.setCurrentItem(pageIndex, false)
    }

    private fun setTabLayoutMediator() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.nextPage()
        } else if (requestCode == PERMISSIONS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.nextPage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel.nextPage()
                }
                return
            }
        }
    }

    private fun observeViewModel() {
        viewModel.currentPageIndex.observe(this) { pageIndex ->
            if (pageIndex == viewModel.onboardingPages.size)
                goToMainActivity()
            loadOnboardingPage(pageIndex)
        }
    }

    private fun loadOnboardingPage(pageIndex: Int) {
        binding.viewPager.setCurrentItem(pageIndex, true)
        binding.viewPager.isVisible = true
        binding.tabLayout.isVisible = true
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }
}
