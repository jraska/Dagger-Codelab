package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary

class LibraryEventAnalytics(
  val library: AnalyticsLibrary,
  val analyticsFilter: AnalyticsFilter
) : EventAnalytics {
  override fun reportEvent(event: AnalyticsEvent) {
    if (analyticsFilter.accept(event.key)) {
      return library.reportEvent(event.key, event.properties)
    }
  }
}
