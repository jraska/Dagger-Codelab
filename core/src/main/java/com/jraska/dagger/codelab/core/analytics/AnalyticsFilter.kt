package com.jraska.dagger.codelab.core.analytics

import javax.inject.Inject

private val MAX_KEY_LENGTH = 24

class AnalyticsFilter @Inject constructor() {
  fun accept(key: String): Boolean {
    return key.length < MAX_KEY_LENGTH
  }
}
