package com.jraska.dagger.codelab.core.analytics.config

interface RemoteConfig {
  fun getBoolean(key: String): Boolean
}
