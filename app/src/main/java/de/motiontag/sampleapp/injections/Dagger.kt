package de.motiontag.sampleapp.injections

import android.app.Application
import de.motiontag.sampleapp.injections.components.AppComponent
import de.motiontag.sampleapp.injections.components.DaggerProductionComponent
import de.motiontag.sampleapp.injections.modules.AppModule

/**
 * Singleton class to take care of dependency injection with Dagger 2.
 */
object Dagger {
    private const val MESSAGE = "Dagger is not initialized"
    private var component: AppComponent? = null

    fun getComponent(): AppComponent {
        if (component == null) throw IllegalStateException(MESSAGE)
        return component as AppComponent
    }

    fun init(application: Application) {
        this.component = DaggerProductionComponent.builder()
            .appModule(AppModule(application))
            .build()
    }
}
