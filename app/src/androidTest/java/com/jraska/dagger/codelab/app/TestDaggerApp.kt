package com.jraska.dagger.codelab.app

import android.content.Context
import androidx.test.rule.ActivityTestRule
import com.jraska.dagger.codelab.app.di.AppComponent
import com.jraska.dagger.codelab.config.di.ConfigModule
import com.jraska.dagger.codelab.core.analytics.AnalyticsEvent
import com.jraska.dagger.codelab.core.analytics.EventAnalytics
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

class TestDaggerApp : DaggerApp() {
  val fakeAnalytics =  (appComponent() as TestAppComponent).fakeEventAnalytics()

  override fun createDaggerComponent(): AppComponent {
    return DaggerTestAppComponent.factory().create(this)
  }
}

fun ActivityTestRule<*>.reportedAnalytics(): List<AnalyticsEvent> {
  return (activity.application as TestDaggerApp).fakeAnalytics.reportedAnalytics
}

@Singleton
@Component(modules = [ConfigModule::class, FakeAnalyticsModule::class])
interface TestAppComponent : AppComponent {
  fun fakeEventAnalytics(): FakeEventAnalytics

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance context: Context): TestAppComponent
  }
}

@Module
object FakeAnalyticsModule {

  @Provides
  @Singleton
  fun fakeEventAnalytics(): FakeEventAnalytics = FakeEventAnalytics()

  @Provides
  fun eventAnalytics(fakeEventAnalytics: FakeEventAnalytics): EventAnalytics = fakeEventAnalytics
}
