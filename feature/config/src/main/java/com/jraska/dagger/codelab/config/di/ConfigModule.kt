package com.jraska.dagger.codelab.config.di

import com.jraska.dagger.codelab.config.InMemoryConfig
import com.jraska.dagger.codelab.core.config.RemoteConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ConfigModule {
  @Provides
  fun remoteConfig(inMemoryConfig: InMemoryConfig): RemoteConfig = inMemoryConfig

  @Provides
  @Singleton
  fun inMemoryConfig() = InMemoryConfig()
}
