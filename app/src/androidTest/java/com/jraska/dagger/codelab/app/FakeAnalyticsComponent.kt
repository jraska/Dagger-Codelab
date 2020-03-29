package com.jraska.dagger.codelab.app

import com.jraska.dagger.codelab.core.analytics.di.AnalyticsComponent
import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent
import com.jraska.dagger.codelab.core.analytics.EventAnalytics

class FakeAnalyticsComponent : AnalyticsComponent {
  val reportedAnalytics = mutableListOf<AnalyticsEvent>()

  private val fakeEventAnalytics = object : EventAnalytics {
    override fun reportEvent(event: AnalyticsEvent) {
      reportedAnalytics.add(event)
    }
  }

  override fun eventAnalytics(): EventAnalytics {
    return fakeEventAnalytics
  }
}
