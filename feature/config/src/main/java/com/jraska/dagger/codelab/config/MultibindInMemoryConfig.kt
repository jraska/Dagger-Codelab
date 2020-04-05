package com.jraska.dagger.codelab.config

import javax.inject.Inject
import javax.inject.Provider

class MultibindInMemoryConfig @Inject constructor(
  val initialState: Map<String, @JvmSuppressWildcards Provider<Boolean>>
) : MutableConfig {
  private val configs = mutableMapOf<String, Boolean>()
    .withDefault { initialState[it]?.get() ?: false }

  override fun set(key: String, value: Boolean) {
    configs[key] = value
  }

  override fun keys(): Set<String> = initialState.keys

  override fun getBoolean(key: String): Boolean {
    return configs.getValue(key)
  }
}
