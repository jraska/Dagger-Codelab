package com.jraska.dagger.codelab.core.analytics.di

import com.jraska.dagger.codelab.core.analytics.EventAnalytics
interface AnalyticsComponent {
   fun eventAnalytics(): EventAnalytics
}
