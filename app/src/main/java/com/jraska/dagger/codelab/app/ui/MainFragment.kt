package com.jraska.dagger.codelab.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jraska.dagger.codelab.app.DaggerApp
import com.jraska.dagger.codelab.app.R
import com.jraska.dagger.codelab.config.di.CONFIG_BYE_BUTTON
import com.jraska.dagger.codelab.core.config.RemoteConfig
import javax.inject.Inject

class MainFragment : Fragment() {

  @Inject
  lateinit var config: RemoteConfig

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerApp.of(this).appComponent.inject(this)

    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_main, container, false)
  }

  override fun onResume() {
    super.onResume()

    val byeButton = view!!.findViewById<View>(R.id.main_bye_button)
    if (config.getBoolean(CONFIG_BYE_BUTTON)) {
      byeButton.visibility = View.VISIBLE
    } else {
      byeButton.visibility = View.GONE
    }
  }
}
