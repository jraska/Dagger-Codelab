package com.jraska.dagger.codelab.core.analytics

interface EventAnalytics {
  fun reportEvent(event: AnalyticsEvent)
}
