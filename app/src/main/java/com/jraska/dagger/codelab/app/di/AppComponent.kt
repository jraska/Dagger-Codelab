package com.jraska.dagger.codelab.app.di

import android.content.Context
import com.jraska.dagger.codelab.app.ui.MainActivity
import com.jraska.dagger.codelab.core.analytics.di.AnalyticsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AnalyticsModule::class])
interface AppComponent {
  fun inject(mainActivity: MainActivity)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setContext(context: Context): Builder

    fun build(): AppComponent
  }
}
