package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.di.DaggerAnalyticsComponent
import org.junit.Test

class AnalyticsComponentTest {
  @Test
  fun componentDemo() {
    val analyticsComponent = DaggerAnalyticsComponent.create()

    analyticsComponent.eventAnalytics().reportEvent(AnalyticsEvent.create("One"))
    analyticsComponent.eventAnalytics().reportEvent(AnalyticsEvent.create("Two"))

    println((analyticsComponent.eventAnalytics() as LibraryEventAnalytics).library.inMemoryData)
  }
}
