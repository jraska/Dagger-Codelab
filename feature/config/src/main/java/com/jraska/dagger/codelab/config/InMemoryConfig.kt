package com.jraska.dagger.codelab.config

import com.jraska.dagger.codelab.core.analytics.config.RemoteConfig
import javax.inject.Inject

internal class InMemoryConfig @Inject constructor() : RemoteConfig {
  private val configs = mutableMapOf<String, Boolean>("bye_button" to true)

  override fun getBoolean(key: String): Boolean {
    return configs[key] ?: false
  }
}
