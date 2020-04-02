package com.jraska.dagger.codelab.app.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.jraska.dagger.codelab.app.R
import com.jraska.dagger.codelab.app.reportedAnalytics
import com.jraska.dagger.codelab.app.ui.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class AppTest {
  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun clickingDisablesButton() {
    onView(withId(R.id.main_bye_button)).check(matches(isDisplayed()))

    onView(withId(R.id.fab)).perform(click())
//    assertEventReported()
    onView(withText("bye_button")).perform(click())

    pressBack()

    onView(withId(R.id.main_bye_button)).check(matches(not(isDisplayed())))
  }

  private fun assertEventReported() {
    val event = activityRule.reportedAnalytics().findLast { it.key == "main_onFabClick" }
    if (event == null) {
      throw IllegalStateException("Fab event click not found")
    }
  }
}
