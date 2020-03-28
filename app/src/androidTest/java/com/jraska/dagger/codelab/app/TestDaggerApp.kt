package com.jraska.dagger.codelab.app

import androidx.test.rule.ActivityTestRule
import com.jraska.dagger.codelab.core.analytics.AnalyticsComponent
import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent

class TestDaggerApp : DaggerApp() {
  val fakeAnalyticsComponent = FakeAnalyticsComponent()

  override fun createAnalyticsComponent(): AnalyticsComponent {
    return fakeAnalyticsComponent
  }
}

fun ActivityTestRule<*>.reportedAnalytics(): List<AnalyticsEvent> {
  return (activity.application as TestDaggerApp).fakeAnalyticsComponent.reportedAnalytics
}
