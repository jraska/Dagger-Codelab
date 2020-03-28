package com.jraska.dagger.codelab.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

@Suppress("unused") // used in build.gradle
class TestRunner : AndroidJUnitRunner() {
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, TestDaggerApp::class.qualifiedName, context)
  }
}
