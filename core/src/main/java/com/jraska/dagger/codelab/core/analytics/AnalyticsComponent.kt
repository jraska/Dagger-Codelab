package com.jraska.dagger.codelab.core.analytics

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AnalyticsModule::class])
interface AnalyticsComponent : Analytics {
  override fun eventAnalytics(): EventAnalytics
}
