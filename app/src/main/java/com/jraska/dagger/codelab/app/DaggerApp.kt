package com.jraska.dagger.codelab.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.jraska.dagger.codelab.app.di.AppComponent
import com.jraska.dagger.codelab.app.di.DaggerAppComponent
import com.jraska.dagger.codelab.core.analytics.DaggerAnalyticsComponent

class DaggerApp : Application() {
//  val analyticsComponent: AnalyticsComponent by lazy { DaggerAnalyticsComponent.create() }

  val appComponent: AppComponent by lazy {
    DaggerAppComponent.builder()
      .context(this)
      .build()
  }

  override fun onCreate() {
    super.onCreate()
  }

  companion object {
    fun of(activity: Activity): DaggerApp {
      return activity.application as DaggerApp
    }

    fun of(fragment: Fragment): DaggerApp {
      return of(fragment.activity!!)
    }
  }
}
