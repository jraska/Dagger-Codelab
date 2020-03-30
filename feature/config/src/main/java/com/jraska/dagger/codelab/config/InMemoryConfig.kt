package com.jraska.dagger.codelab.config

import com.jraska.dagger.codelab.config.di.CONFIG_BYE_BUTTON
import javax.inject.Inject

class InMemoryConfig @Inject constructor() : MutableConfig {
  private val configs = mutableMapOf(CONFIG_BYE_BUTTON to true)

  override fun set(key: String, value: Boolean) {
    configs[key] = value
  }

  override fun keys(): Set<String> = configs.keys

  override fun getBoolean(key: String): Boolean {
    return configs[key] ?: false
  }
}
