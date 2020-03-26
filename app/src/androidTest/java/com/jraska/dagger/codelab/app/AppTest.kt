package com.jraska.dagger.codelab.app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
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
    onView(withText("bye_button")).perform(click())

    pressBack()

    onView(withId(R.id.main_bye_button)).check(matches(not(isDisplayed())))
  }
}
