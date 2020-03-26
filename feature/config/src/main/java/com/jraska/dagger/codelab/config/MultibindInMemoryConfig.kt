package com.jraska.dagger.codelab.config

import javax.inject.Inject

class MultibindInMemoryConfig @Inject constructor(
  @JvmSuppressWildcards
  val initialState: Map<String, Boolean>
) : MutableConfig {
  private val configs = initialState.toMutableMap()

  override fun set(key: String, value: Boolean) {
    configs[key] = value
  }

  override fun keys(): Set<String> = configs.keys

  override fun getBoolean(key: String): Boolean {
    return configs[key] ?: false
  }
}
