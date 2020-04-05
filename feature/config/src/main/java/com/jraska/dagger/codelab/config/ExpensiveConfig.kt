package com.jraska.dagger.codelab.config

import com.jraska.dagger.codelab.core.app.OnAppCreate
import java.util.concurrent.Executors

object ExpensiveConfig {
  const val KEY = "expensive_to_load"

  val value by lazy {
    Thread.sleep(1000) // I/O etc.
    true
  }

  fun initialize() = value

  object Initializer : OnAppCreate {
    override fun onCreate() {
      Executors.newSingleThreadExecutor().submit { initialize() } // DON'T copy - use Coroutines, RxJava or some other sane stuff
    }
  }
}
