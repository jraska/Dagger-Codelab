package com.jraska.dagger.codelab.core

import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent
import com.jraska.dagger.codelab.core.analytics.DaggerAnalyticsComponent
import com.jraska.dagger.codelab.core.analytics.LibraryEventAnalytics
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
