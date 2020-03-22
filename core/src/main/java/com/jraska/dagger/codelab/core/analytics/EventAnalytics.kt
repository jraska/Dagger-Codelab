package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.AnalyticsFilter
import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventAnalytics @Inject constructor(
  val library: AnalyticsLibrary,
  val analyticsFilter: AnalyticsFilter
) {
  fun reportEvent(key: String, properties: Map<String, Any> = emptyMap()) {
    if (analyticsFilter.accept(key)) {
      return library.reportEvent(key, properties)
    }
  }
}
