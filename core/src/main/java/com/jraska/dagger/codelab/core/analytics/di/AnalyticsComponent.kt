package com.jraska.dagger.codelab.core.analytics.di

import com.jraska.dagger.codelab.core.analytics.EventAnalytics
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AnalyticsModule::class])
interface AnalyticsComponent {
  fun eventAnalytics(): EventAnalytics
}
