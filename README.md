

[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

# Dagger-Codelab - Section 06a Hilt option - Migration to Hilt

## What you will learn
- How to setup Hilt in the app
  - How to migrate existing components into Dagger Hilt
  - Using Hilt-specific annotations
  - Injection Application, Activities and Fragments with Hilt

# Goal
- Migrate or application code to use Hilt
- Delete `AppComponent` interface and other boilerplate

# Section 06a Hilt option - Migration to Hilt - Instructions
We will migrate all our production code to use Dagger Hilt. All necessary dependencies are set up properly and we will be only changing annotations. For setup instructions please check the [Gradle setup](https://dagger.dev/hilt/gradle-setup) section.

## Task 1: Make the DaggerApp injectable with Hilt
When Hilt is already setup injecting the `Application` object happens automatically when added the `@HiltAndroidApp` annotation.

```
// DaggerApp.kt
@HiltAndroidApp
open class DaggerApp...
```
  That's it! You can now add `@Inject` annotated fields into the `DaggerApp`. If you want to quickly try some easy one, one option is `AnalyticsFilter`.

## Task 2: Install all modules into the `SingletonComponent`
Since we have old `AppComponent` as a singleton, we can simply annotate its modules with `@InstallIn(SingletonComponent::class) `
```
// AnalyticsModule.kt
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
```
```
// ConfigModule.kt
@InstallIn(SingletonComponent::class)
object ConfigModule {
```

Our app is now ready for injection.

## Task 3. Inject `OnAppCreate` actions into `DaggerApp`
Add the injected `Set<OnAppCreate>` field inside and execute them after `super.onCreate()`

```
// DaggerApp.kt
@Inject
lateinit var onAppCreateActions: Set<@JvmSuppressWildcards OnAppCreate>
...
override fun onCreate() {
  super.onCreate()
  onAppCreateActions.forEach { it.onCreate() }
}
```

Hint: App now compiles and runs fine, however the `DaggerApp` now references different instances of `OnAppCreate`, than the rest of the app, because it is still injected by `AppComponent`.

## Task 4: Inject `MainActivity`
Each Android component needs to be annotated with the `@AndroidEntryPoint` annotation. We can also remove the `inject` call.

```
// MainActivity.kt
@AndroidEntryPoint
class MainActivity
...
// delete the "DaggerApp.of(this).appComponent.inject(this)" line.

```

If you would try to build the app now, the build will crash on `PackageName` missing the `Context` binding. Hilt offers `@ApplicationContext` qualifier annotation to explicitly declare which context we inject.
```
// PackageName.kt
@ApplicationContext val context: Context
```

You can now also delete the `fun inject(mainActivity: MainActivity)` from `AppComponent`.

## Task 5: Inject `MainFragment`
We can continue within the migration to Hilt by adding the `@AndroidEntryPoint` again and removing the `inject(MainFragment)` call. Before migrating the `MainFragment`, please check that when clicking on the FAB and disabling the `bye_button` , the button disappears.

```
// MainFragment.kt
@AndroidEntryPoint
class MainFragment

// Delete whole `onCreate` method override as the injection was the single action.
```

⚠ When you will run the app, you can see something strange.

The `bye_button` config no longer works. Why?
<details>
  <summary>Expand for answer</summary>

  There are 2 different instances of `RemoteConfig` even if it is marked as `@Singleton` in `ConfigModule`. The reason for that is there are now actually 2 independent components.

  1. Hilt `DaggerDaggerApp_HiltComponents_SingletonC` injecting `MainFragment`
  2. `DaggerAppComponent` injecting `ConfigActivity`

Each of these components is a separate world even if they share `ConfigModule`, however they don't share any instances!

This may cause pain and unexpected bugs whilst migrating to Hilt.

To share instances, you can for example use some proxy module, pulling your `AppComponent` from somewhere (probably static :( ) and providing certain dependencies to Hilt. If you know about nicer solution how to share instances between `AppComponent` and Hilt singleton component, please open an issue. Thanks!
</details>

## Task 6: Inject ConfigActivity
Let's fix the bug above by moving `ConfigActivity` into Hilt world.

```
// ConfigActivity.kt
@AndroidEntryPoint
class ConfigActivity

// Delete `ConfigComponent`, `inject` call and related code.
```
When we run the app again, the bug is fixed and everything works as before again.

## Task 7: Delete `AppComponent`
We now moved fully our production code to Hilt generated components. It's time for cleanup.
We can now delete `AppComponent.kt`, `HasAppComponent.kt` and all related code in `DaggerApp.kt`.

The final `DaggerApp.kt` should look like:
```
@HiltAndroidApp
open class DaggerApp : Application() {
  @Inject
  lateinit var onAppCreateActions: Set<@JvmSuppressWildcards OnAppCreate>

  override fun onCreate() {
    super.onCreate()
    onAppCreateActions.forEach { it.onCreate() }
  }
}
```

Here we can see the main intentions of Dagger Hilt - removing common setup boilerplate, manual `inject` calls and need for interfaces like `HasAppComponent` and `ConfigComponent` to wire things together.


**Due to changed Dagger structure, our tests will not compile anymore. Let's fix them in [07-hilt-option-testing](https://github.com/jraska/Dagger-Codelab/tree/07-hilt-option-testing)**
