package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.di.DaggerAnalyticsComponent
import org.junit.Test

class AnalyticsComponentTest {
  @Test
  fun componentDemo() {
    val analyticsComponent = DaggerAnalyticsComponent.create()

    analyticsComponent.eventAnalytics().reportEvent(AnalyticsEvent.create("One"))
    analyticsComponent.eventAnalytics().reportEvent(AnalyticsEvent.create("Two"))

    val analyticsReported = (analyticsComponent.eventAnalytics() as LibraryEventAnalytics).library.inMemoryData
    println(analyticsReported)
    assert(analyticsReported.size == 2)
  }
}
