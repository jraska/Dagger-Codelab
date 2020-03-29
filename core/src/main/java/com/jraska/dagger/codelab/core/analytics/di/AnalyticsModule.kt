package com.jraska.dagger.codelab.core.analytics.di

import com.jraska.dagger.codelab.core.analytics.EventAnalytics
import com.jraska.dagger.codelab.core.analytics.LibraryEventAnalytics
import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AnalyticsModule {

  @Provides
  fun analyticsLibrary(): AnalyticsLibrary = AnalyticsLibrary()

  @Provides
  @Singleton
  fun eventAnalytics(implementation: LibraryEventAnalytics): EventAnalytics = implementation
}
