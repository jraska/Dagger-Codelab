package com.jraska.dagger.codelab.app

import androidx.test.rule.ActivityTestRule
import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent

class TestDaggerApp : DaggerApp() {
  val fakeAnalytics = FakeEventAnalytics()
}

fun ActivityTestRule<*>.reportedAnalytics(): List<AnalyticsEvent> {
  return (activity.application as TestDaggerApp).fakeAnalytics.reportedAnalytics
}
