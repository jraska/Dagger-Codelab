package com.jraska.dagger.codelab.core.analytics

import com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary
import dagger.Module
import dagger.Provides

@Module
object AnalyticsModule {

  @Provides
  fun analyticsLibrary(): AnalyticsLibrary = AnalyticsLibrary()
}
