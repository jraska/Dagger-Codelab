package com.jraska.dagger.codelab.config.di

import com.jraska.dagger.codelab.config.ui.ConfigActivity

const val CONFIG_BYE_BUTTON = "bye_button"

interface ConfigComponent {
  fun inject(configActivity: ConfigActivity)
}
