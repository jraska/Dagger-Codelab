package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary
import javax.inject.Inject

class LibraryEventAnalytics @Inject constructor(
  val library: AnalyticsLibrary,
  private val analyticsFilter: AnalyticsFilter
) : EventAnalytics {
  override fun reportEvent(event: AnalyticsEvent) {
    if (analyticsFilter.accept(event.key)) {
      return library.reportEvent(event.key, event.properties)
    }
  }
}
