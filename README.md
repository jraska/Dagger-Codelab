
[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

# Dagger-Codelab - Section 5 - Instrumented Tests

## What you will learn
- Instrumented Testing
  - How to replace dependencies within androidTest.
  - Using component dependency to replace dependencies in tests.
  - How to assert on your analytic events.

# Section 5: Instrumented Tests - Instructions
In this section, we will see how we can utilise Dagger to switch dependencies within tests.

Until now, we could have seen commented method call within our `AppTest`.
```
// AppTest.kt
//    assertEventReported()
```

To make this possible, the following setup will tweak previous `EventAnalytics` implementation to be `FakeEventAnalytics`.

## Task 1: Make DaggerAppComponent overridable
One way how to tweak things within instrumented tests is to do this within the application object. We can see within `TestRunner`, that we will create instance of `TestDaggerApp` instead of only `DaggerApp` within tests.

To be able to override the `AppComponent` we need to extract its instance creation into separate `open` method.

```
// DaggerApp.kt
val appComponent: AppComponent by lazy {
  createDaggerComponent()
}

open fun createDaggerComponent(): AppComponent {
  return DaggerAppComponent.builder()
    .setContext(this)
    .build()
}
```

## Task 2: Create fake module for analytics
To be able to replace `EventAnalytics` interface with some test double, we have to create a `@Module` satisfying required dependencies - `EventAnalytcs` in our case.
```
@Module  // TestDaggerApp.kt
object FakeAnalyticsModule {

  @Provides
  @Singleton
  fun fakeEventAnalytics(): FakeEventAnalytics = FakeEventAnalytics()

  @Provides
  fun eventAnalytics(fakeEventAnalytics: FakeEventAnalytics): EventAnalytics = fakeEventAnalytics
}
```

## Task 3: Create new component for tests
Now it's time to create a different composition of available modules. The main requirement is the new component will implement the `AppComponent` interface to satisfy all necessary injections etc. The new component will contain `ConfigModule` and `FakeAnalyticsModule` We can also use `@Component.Factory` instead  of `@Component.Builder`, to see different way of setting up components.

```
@Singleton  // TestDaggerApp.kt
@Component(modules = [ConfigModule::class, FakeAnalyticsModule::class])
interface TestAppComponent : AppComponent {
  fun fakeEventAnalytics(): FakeEventAnalytics

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance context: Context): TestAppComponent
  }
}
```

## Task 4: Tell Gradle to run Dagger within kapt
We are used to add usually just `kapt`, but to run Dagger annotation processing also for testing sources, we need to add it as `kaptAndroidTest`.

```
// app/build.gradle
kaptAndroidTest 'com.google.dagger:dagger-compiler:2.27'
```

## Task 5: Use TestAppComponent and expose `FakeAnalytics`
Now we need to make sure we create an instance of newly generated `DaggerTestAppComponent` and our property `fakeAnalytics` will return correct instance.

```
// TestDaggerApp.kt
val fakeAnalytics =  (appComponent() as TestAppComponent).fakeEventAnalytics()

override fun createDaggerComponent(): AppComponent {
  return DaggerTestAppComponent.factory().create(this)
}
```
The `AppTest` with uncommented `assertEventReported()` method will now pass.

**We can see the whole solution now at [master](https://github.com/jraska/Dagger-Codelab/tree/master)**

### Optional tasks
- Have a look at `ConfigTest` and `ConfigTestApp` to see how simple test within module can look like.
- Try to add `EventAnalytics` dependency into `ConfigActivity` - what happens with the `ConfigTest`? How to fix it?

# 🎉End of the Codelab 🎉 [Feedback form](https://forms.gle/Nfz49ZZGJUXP9r1R7)
Congratulations! - this is all for now to demonstrate some ways how to utilise Dagger features to enjoy flexible and powerful DI within your app.

There is still lot to learn and try, but hopefully this Codelab can serve you as a reasonable basement, playground and learning resource.

If you feel like there is anything to fix, improve or any other feedback, please open an [Issue]([https://github.com/jraska/Dagger-Codelab/issues/new](https://github.com/jraska/Dagger-Codelab/issues/new)) and also any  [PR]([https://github.com/jraska/Dagger-Codelab/pulls](https://github.com/jraska/Dagger-Codelab/pulls)) is welcome.

Happy coding with Dagger!

