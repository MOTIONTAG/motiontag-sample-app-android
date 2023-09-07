package de.motiontag.sampleapp.injections.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule(private val application: Application) {

    @Provides @Singleton
    internal fun application(): Application = application

}
