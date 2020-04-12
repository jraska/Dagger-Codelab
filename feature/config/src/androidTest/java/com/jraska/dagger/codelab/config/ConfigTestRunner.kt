 package com.jraska.dagger.codelab.config

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

@Suppress("unused") // used in build.gradle
class ConfigTestRunner : AndroidJUnitRunner() {
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, ConfigTestApp::class.qualifiedName, context)
  }
}
