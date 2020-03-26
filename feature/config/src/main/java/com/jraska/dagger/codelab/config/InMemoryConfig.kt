package com.jraska.dagger.codelab.config

import javax.inject.Inject

class InMemoryConfig @Inject constructor() : MutableConfig {
  private val configs = mutableMapOf("bye_button" to true)

  override fun set(key: String, value: Boolean) {
    configs[key] = value
  }

  override fun keys(): Set<String> = configs.keys

  override fun getBoolean(key: String): Boolean {
    return configs[key] ?: false
  }
}
