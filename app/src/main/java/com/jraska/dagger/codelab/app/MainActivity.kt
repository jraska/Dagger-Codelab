package com.jraska.dagger.codelab.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jraska.dagger.codelab.config.ConfigActivity
import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent
import com.jraska.dagger.codelab.core.analytics.EventAnalytics
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var eventAnalytics: EventAnalytics

  @Inject
  lateinit var stringProvider: StringProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))

//    eventAnalytics = DaggerApp.of(this).analyticsComponent.eventAnalytics()
    DaggerApp.of(this).appComponent.inject(this)

    findViewById<View>(R.id.fab).setOnClickListener {
      eventAnalytics.reportEvent(AnalyticsEvent.create("main_onFabClick"))
      ConfigActivity.start(this)
    }
  }
}
