package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.di.AnalyticsComponent
import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary
import org.junit.Test

class AnalyticsComponentTest {
  @Test
  fun componentDemo() {
    val analyticsComponent = object : AnalyticsComponent {
      val eventAnalytics = LibraryEventAnalytics(AnalyticsLibrary(), AnalyticsFilter())

      override fun eventAnalytics(): EventAnalytics {
        return eventAnalytics
      }
    }

    analyticsComponent.eventAnalytics().reportEvent(AnalyticsEvent.create("One"))
    analyticsComponent.eventAnalytics().reportEvent(AnalyticsEvent.create("Two"))

    val analyticsReported = (analyticsComponent.eventAnalytics() as LibraryEventAnalytics).library.inMemoryData
    println(analyticsReported)
    assert(analyticsReported.size == 2)
  }
}
