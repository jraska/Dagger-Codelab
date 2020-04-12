package com.jraska.dagger.codelab.config

import android.app.Application
import com.jraska.dagger.codelab.config.di.ConfigComponent
import com.jraska.dagger.codelab.config.di.ConfigModule
import com.jraska.dagger.codelab.core.di.HasAppComponent
import dagger.Component
import javax.inject.Singleton

class ConfigTestApp : Application(), HasAppComponent{
  override fun appComponent(): Any {
    return DaggerConfigTestComponent.create()
  }
}

@Singleton
@Component(modules = [ConfigModule::class])
interface ConfigTestComponent : ConfigComponent
