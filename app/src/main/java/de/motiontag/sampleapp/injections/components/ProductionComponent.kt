package de.motiontag.sampleapp.injections.components

import dagger.Component
import de.motiontag.sampleapp.injections.modules.AppModule
import de.motiontag.sampleapp.injections.modules.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface ProductionComponent : AppComponent
