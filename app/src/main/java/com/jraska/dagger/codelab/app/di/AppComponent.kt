package com.jraska.dagger.codelab.app.di

import android.content.Context
import com.jraska.dagger.codelab.app.ui.MainActivity
import com.jraska.dagger.codelab.app.ui.MainFragment
import com.jraska.dagger.codelab.config.di.ConfigComponent
import com.jraska.dagger.codelab.config.di.ConfigModule
import com.jraska.dagger.codelab.core.analytics.di.AnalyticsModule
import com.jraska.dagger.codelab.core.app.OnAppCreate
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AnalyticsModule::class, ConfigModule::class])
interface AppComponent : ConfigComponent {

  fun onAppCreateActions(): Set<OnAppCreate>

  fun inject(mainFragment: MainFragment)

  fun inject(mainActivity: MainActivity)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setContext(context: Context): Builder

    fun build(): AppComponent
  }
}
