package com.jraska.dagger.codelab.config

import com.jraska.dagger.codelab.core.config.RemoteConfig

interface MutableConfig : RemoteConfig {
  fun keys(): Set<String>

  fun set(key: String, value: Boolean)
}
