package com.jraska.dagger.codelab.core.config

interface RemoteConfig {
  fun getBoolean(key: String): Boolean
}
