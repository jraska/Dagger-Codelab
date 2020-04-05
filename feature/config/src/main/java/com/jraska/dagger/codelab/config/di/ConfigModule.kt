package com.jraska.dagger.codelab.config.di

import com.jraska.dagger.codelab.config.MultibindInMemoryConfig
import com.jraska.dagger.codelab.config.MutableConfig
import com.jraska.dagger.codelab.core.config.RemoteConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@Module
object ConfigModule {

  @Singleton
  @Provides
  fun remoteConfig(config: MultibindInMemoryConfig): MutableConfig = config

  @Provides
  fun inMemoryConfig(config: MutableConfig): RemoteConfig = config

  @Provides
  @IntoMap
  @StringKey(CONFIG_BYE_BUTTON)
  fun byeButtonConfig() = true
}
