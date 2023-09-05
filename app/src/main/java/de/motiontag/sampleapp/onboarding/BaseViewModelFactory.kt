package de.motiontag.sampleapp.onboarding

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) {
        ViewModelProvider(this)[T::class.java]
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator))[T::class.java]
    }
}
