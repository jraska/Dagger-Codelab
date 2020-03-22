package com.jraska.dagger.codelab.core

import com.jraska.dagger.codelab.core.analytics.DaggerAnalyticsComponent
import org.junit.Test

class AnalyticsComponentTest {
  @Test
  fun componentDemo() {
    val analyticsComponent = DaggerAnalyticsComponent.create()

    analyticsComponent.eventAnalytics().reportEvent("One")
    analyticsComponent.eventAnalytics().reportEvent("Two")

    println(analyticsComponent.eventAnalytics().library.inMemoryData)
  }
}
