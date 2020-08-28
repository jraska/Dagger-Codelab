package com.jraska.dagger.codelab.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.jraska.dagger.codelab.core.app.OnAppCreate
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
open class DaggerApp : Application() {

  @Inject
  lateinit var onAppCreateActions: Set<@JvmSuppressWildcards OnAppCreate>

  override fun onCreate() {
    super.onCreate()
    onAppCreateActions.forEach { it.onCreate() }
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
