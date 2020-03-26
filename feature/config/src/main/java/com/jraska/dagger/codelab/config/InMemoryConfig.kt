package com.jraska.dagger.codelab.config

import com.jraska.dagger.codelab.core.config.RemoteConfig

class InMemoryConfig : RemoteConfig {
  private val configs = mutableMapOf("bye_button" to true)

  internal fun set(key: String, value: Boolean) {
    configs[key] = value
  }

  internal fun keys(): Set<String> = configs.keys

  override fun getBoolean(key: String): Boolean {
    return configs[key] ?: false
  }
}
