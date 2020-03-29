package com.jraska.dagger.codelab.core.analytics.di

import com.jraska.dagger.codelab.core.analytics.LibraryEventAnalytics

interface AnalyticsComponent {
  fun eventAnalytics(): LibraryEventAnalytics
}
