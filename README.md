

[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

# Dagger-Codelab - Section 07a Hilt option - Testing with Hilt

## What you will learn
- How to setup Hilt in tests
- How to migrate existing tests into Dagger Hilt
- Using Hilt-specific annotations
- Replacing dependencies in tests

# Goal
- Migrate applications tests to use Hilt
- Discuss the differences between previous solution, its pros and cons.

# Section 07a Hilt option - Testing with Hilt - Instructions
We will migrate all our testing code to use Dagger Hilt. All necessary dependencies are set up properly and we will be only changing annotations. For setup instructions please check the [Gradle setup](https://dagger.dev/hilt/gradle-setup) section.

## Task 1: Replace the ConfigTestApp in :config module
Hilt needs tests to instantiate the `HiltTestApplication`. We can make our instrumented tests to do so by modifying our `ConfigTestRunner`. We can replace previous `ConfigTestApp` and we can delete `ConfigTestApp.kt` as unused now.

```
// ConfigTestRunner.kt
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, HiltTestApplication::class.qualifiedName, context)
  }

// Delete ConfigTestApp.kt
```

## Task 2: Setup ConfigTest to run with Hilt
Each Hilt test needs to be annotated with `@HiltAndroidTest` and needs to include `HiltAndroidRule`.
```
// ConfigTest.kt
@HiltAndroidTest
class ConfigTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)
```

We can now run the `ConfigTest`, it will pass. 🎉


## Task 3: Replace the TestDaggerApp in :app module and setup AppTest to run with Hilt
Similarly like in previous case, we need to replace `TestDaggerApp` for `HiltTestApplication`, we can delete the `TestDaggerApp` and we need to add `@HiltAndroidTest` with `HiltAndroidRule`.
```
// TestRunner.kt
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, HiltTestApplication::class.qualifiedName, context)
  }

// Delete TestDaggerApp.kt
```
```
// AppTest.kt
@HiltAndroidTest
class AppTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)
```

## Task 4: Replace the EventAnalytics dependency to assert analytic events
We now need to use `FakeEventAnalytics` and bind them with `@BindValue` to Hilt Singleton component. We also need to uninstall the original `AnalyticsModule` to allow replacing of the dependency.

```
// AppTest.kt
@HiltAndroidTest
@UninstallModules(AnalyticsModule::class)
class AppTest {
...
  val fakeEventAnalytics = FakeEventAnalytics()

  @BindValue
  @JvmField // Needs to be here because of Kotlin
  val eventAnalytics: EventAnalytics = fakeEventAnalytics
...

  private fun assertEventReported() {
    val event = fakeEventAnalytics.reportedAnalytics.findLast { it.key == "main_onFabClick" }
...
}
```
`AppTest` will now pass.

## Task 5: Understand how Hilt works with testing
- It is a very good exercise to inspect the generated code and see how Hilt composes the components for the tests. You can jump into the code by finding usages of `eventAnalytics` field.
- By browsing we end up within `DaggerAppTest_HiltComponents_SingletonC` component, which is a component created uniquely for `AppTest`. Dagger Hilt therefore generates new Singleton component for each `@HiltAndroidTest` class.
- This component then uses aa generated Factory, which receives instance of the `AppTest` class and pulls out the `FakeEventAnalytics`
- `HiltTestApplication` then finds the unique component by using the `TestApplicationComponentManager`.

**The solution of this section can be found in [hilt-option-solution](https://github.com/jraska/Dagger-Codelab/tree/hilt-option-solution)**

## Discussion - Testing philosophy

- Testing philosophy is very well explained in the [Dagger Hilt documentation](https://dagger.dev/hilt/testing-philosophy). It's a brilliant article evaluating pros and cons of different approaches.
- Separate component per test - Hilt instantiates a brand new component instance per test. This is nice and clean however has a high risk in brownfield projects which would keep state in some static place.
- Build time concern - As the time goes, there might be a huge amount of different components for testing and the kapt step might become too long. This should be measured though and could be a topic of separate article.

## Discussion - Published bindings

Published bindings are explained in  [Testing Guide for regular Dagger](https://dagger.dev/dev-guide/testing.html), section. "Organize modules for testability"

Published binding means a binding, which is used outside of publishing Dagger module. Having only one published binding per module has an advantage, that when this module is not used/uninstalled,
there has to be only one dependency to fake and replacing modules becomes easy to achieve correct binding graph. If there is too many published bindings, the faking becomes hard and inconvenient.

However the best practice is difficult to enforce, because we usually don't have full visibility whilst looking on the module which of the provided dependencies re being used out of the module.
A good practice can be having all methods publishing Kotlin `internal` types except one method publishing a public one and utilise Gradle modules to get advantage of the internal visibility.
Other ideas for a practice or enforcement are up to discussion. Feel free to start an issue.
