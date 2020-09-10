package com.jraska.dagger.codelab.core.analytics.di

import com.jraska.dagger.codelab.core.analytics.EventAnalytics
import com.jraska.dagger.codelab.core.analytics.LibraryEventAnalytics
import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
object AnalyticsModule {

  @Provides
  @Singleton
  fun provideAnalyticsLibrary(): AnalyticsLibrary {
    return AnalyticsLibrary()
  }

  @Provides
  fun provideEventAnalytics(implementation: LibraryEventAnalytics): EventAnalytics {
    return implementation
  }
}
