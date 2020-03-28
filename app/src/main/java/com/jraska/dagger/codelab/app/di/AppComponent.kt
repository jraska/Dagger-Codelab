package com.jraska.dagger.codelab.app.di

import android.content.Context
import com.jraska.dagger.codelab.app.ui.MainActivity
import com.jraska.dagger.codelab.app.ui.MainFragment
import com.jraska.dagger.codelab.config.di.ConfigComponent
import com.jraska.dagger.codelab.config.di.ConfigModule
import com.jraska.dagger.codelab.core.analytics.Analytics
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ConfigModule::class], dependencies = [Analytics::class])
@Singleton
interface AppComponent : ConfigComponent {
  fun inject(activity: MainActivity)

  fun inject(activity: MainFragment)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun context(context: Context): Builder

    fun analytics(analytics: Analytics): Builder

    fun build(): AppComponent
  }
}
