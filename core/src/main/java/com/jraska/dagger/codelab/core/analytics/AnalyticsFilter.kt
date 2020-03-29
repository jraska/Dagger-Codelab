package com.jraska.dagger.codelab.core.analytics

private val MAX_KEY_LENGTH = 24

class AnalyticsFilter {
  fun accept(key: String): Boolean {
    return key.length < MAX_KEY_LENGTH
  }
}
