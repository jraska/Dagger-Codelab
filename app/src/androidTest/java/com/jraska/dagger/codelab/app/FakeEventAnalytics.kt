package com.jraska.dagger.codelab.app

import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent
import com.jraska.dagger.codelab.core.analytics.EventAnalytics

class FakeEventAnalytics : EventAnalytics {
  val reportedAnalytics = mutableListOf<AnalyticsEvent>()

  override fun reportEvent(event: AnalyticsEvent) {
    reportedAnalytics.add(event)
  }
}
