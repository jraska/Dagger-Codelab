package com.jraska.dagger.codelab.config

import com.jraska.dagger.codelab.core.analytics.config.RemoteConfig
import dagger.Binds
import dagger.Module

@Module
abstract class ConfigModule {
  @Binds
  internal abstract fun bindConfig(inMemoryConfig: InMemoryConfig): RemoteConfig
}
