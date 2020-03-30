[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

## What you will learn
- Wiring together with Android
  - Where to create and hold instances of components.
  - Injecting Activities, Injecting Fragments.
  - Using `@BindsInstance` to bind objects we don't own.

# Section 2: Wiring with Android - Instructions
We can use our `EventAnalytics` in practice. This section will happen within module `app` where you can see `DaggerApp`, which will be our `Application` object, `MainActivity`, which will be a class we will be injecting now and also `AppComponent`, which will be holding the main dependency graph of our application.

We can also see `MainFragment` and `PackageName` classes, which are here to demonstrate other features later. Now let's launch the application.

## Task 1: Create instance of AnalyticsComponent within DaggerApp
To start with, we can implement the `createAnalyticsComponent` method within `DaggerApp`:
```
// DaggerApp.kt
private fun createAnalyticsComponent(): AnalyticsComponent = DaggerAnalyticsComponent.create()
```

## Task 2: Pull instance of EventAnalytics from AnalyticsComponent
In case of clicking on FAB within `MainActivity` the application will crash, because `eventAnalytics` field is not initialised. We can do this in `onCreate` method of `MainActivity`.

```
// MainActivity.kt
eventAnalytics = DaggerApp.of(this).analyticsComponent.eventAnalytics()
```
When you click on FAB now, you can now see the analytics event in your logcat:
```
External system interaction - event: main_onFabClick
```
## Task 3: Setup AppComponent
Although you could receive instances from components like this, it doesn't scale well, because you would have to write method for every single type in use. Therefore we can start using our `AppComponent` to inject `MainActivity` and make this interface new Dagger component with `AnalyticsModule`
```
@Singleton // AppComponent.kt
@Component(modules = [AnalyticsModule::class])
interface AppComponent {
```

This will generate `DaggerAppComponent`, which we can instantiate within `DaggerApp`.
```
// DaggerApp.kt
val appComponent: AppComponent by lazy { DaggerAppComponent.create() }
```

## Task 4: Injecting MainActivity
Now it is time to use field injection. Field Injection is common way how to inject instances on Android, because we don't create many objects of the framework. Check [Why Dagger on Android is hard]([https://dagger.dev/android](https://dagger.dev/android)).

For this type of injection we need to write `inject(InjectedType)` method into any component, which contains necessary dependencies.

```
interface AppComponent { // MainActivity.kt
  fun inject(mainActivity: MainActivity)
```

We now need to annotate the field `eventAnalytics` with `@Inject`
```
@Inject // MainActivity.kt
lateinit var eventAnalytics: EventAnalytics
```
The last step is getting an instance of `AppComponent` and call the `inject` method.
```
// MainActivity.kt - before calling super.onCreate(savedInstanceState)
DaggerApp.of(this).appComponent.inject(this)
```
We can also remove the usage of `AnalyticsComponent` here. The app will continue to work the same and we can again see our analytics messages in logcat whilst clicking on the FAB.

## Task 5: Injecting Context - @BindsInstance
Now we can try new field of type `PackageName` and set the title of `MainActivity` to have it as title.
```
@Inject  // MainActivity.kt
lateinit var packageName: PackageName
...
title = packageName.thisAppPackage() // in onCreate method.
```
If we try to run the app, we receive error. We are trying to inject `Context` so we need to instruct Dagger where to get it from.

There are many cases where we need to inject an object **we don't own**. For such cases Dagger brings an option to define `@Component.Builder` interface and enables adding `@BindsInstance` methods, accepting instances of such objects.

```
@Component.Builder // AppComponent.kt
interface Builder {
  @BindsInstance
  fun setContext(context: Context): Builder

  fun build(): AppComponent
}
```
Dagger will generate implementation of `Builder` interface and we can get it with `DaggerAppComponent.builder()` method. The main condition to match is that there is any method, returning the instance of the component -  `fun build(): AppComponent` in our case.

Let's use the builder with `DaggerApp` now:
```
val appComponent: AppComponent by lazy {  // DaggerApp.kt
  DaggerAppComponent.builder()
    .setContext(this)
    .build()
}
```
Now we can run the app and we will see the package name set as title of the activity.

**We can now move into section [03-multiple-modules-setup](https://github.com/jraska/Dagger-Codelab/tree/03-multiple-modules-setup)**

## Optional tasks
- Look inside and `DaggerAppComponent` and discuss how Dagger generates code.
- What is a problem with following version of `appComponent` setup?
 ```
 val appComponent: AppComponent get() {  // DO NOT COPY
  return DaggerAppComponent.builder()
    .setContext(this)
    .build()
}
```
- Why we just don't create `AppComponent` within `MainActivity`. Would it even compile?
