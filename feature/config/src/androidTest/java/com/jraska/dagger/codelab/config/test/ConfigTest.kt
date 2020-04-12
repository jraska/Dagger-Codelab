package com.jraska.dagger.codelab.config.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.jraska.dagger.codelab.config.ui.ConfigActivity
import org.junit.Rule
import org.junit.Test

class ConfigTest {
  @get:Rule
  val activityRule = ActivityTestRule(ConfigActivity::class.java)

  @Test
  fun displaysProperly() {
    onView(withText("bye_button")).check(matches(isDisplayed()))
  }
}
