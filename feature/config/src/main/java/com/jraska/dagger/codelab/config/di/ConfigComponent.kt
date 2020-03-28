package com.jraska.dagger.codelab.config.di

import com.jraska.dagger.codelab.config.ui.ConfigActivity

interface ConfigComponent {
  fun inject(configActivity: ConfigActivity)
}
