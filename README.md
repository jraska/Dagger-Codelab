# Dagger-Codelab - Section 3 - Multi module setup

## What you will learn
- Multi-module project setup
  - How to compose modules together within AppComponent
  - Injecting Activity/Fragment and other dependencies within separate module.
  - Sharing instances with other modules.

# Section 3: Multi module setup - Instructions
Now let's have a look how you can let Dagger smoothly manage dependencies within multi module project. Our focus will be code within directory `feature/config`.

We can see already prepared `ConfigModule`, which we can use to tell Dagger which dependencies belong there. We can see `ConfigActivity`, which we will use to tweak values of our config and also we can see few other files like `InMemoryConfig`, which is a simple `RemoteConfig` implementation and also `MutableConfig`, which we will use to modify config values.

## Task 1: Use RemoteConfig within MainFragment
We can start by adding `@Inject` annotation to the `config` field within and uncommenting the `onResume()` method.
```
@Inject // MainFragment.kt
lateinit var config: RemoteConfig

override fun onCreate(savedInstanceState: Bundle?) {
  DaggerApp.of(this).appComponent.inject(this)
```

We also need to add appropriate inject method and to make Dagger compile, we need to add the `ConfigModule`.
```
// AppComponent.kt
@Component(modules = [AnalyticsModule::class, ConfigModule::class])
interface AppComponent {
  fun inject(mainFragment: MainFragment)
```

## Task 2: Launch the ConfigActivity
We can connect any UI - the easiest one now might be clicking on FAB within `MainActivity`
```
// MainActivity.kt
findViewById<View>(R.id.fab).setOnClickListener {
  ConfigActivity.start(this)
```
We can run the app and click the FAB button. `ConfigActivity` is now crashing and needs to be injected.

## Task 3: Inject ConfigActivity
Injecting within module will be different, from. the reason that the `config` module does not have visibility of `AppComponent` or `DaggerApp`, because that one is within `:app` module and the dependencies look like the following:

<img width="200" alt="Screenshot 2020-04-01 at 00 14 46" src="https://user-images.githubusercontent.com/6277721/78080419-786af280-73ae-11ea-9af7-2840b1399895.png">

We need to somehow bridge this gap. The recommended practice is creating interface within module `ConfigComponent` with the desired `inject` method.
```
interface ConfigComponent {  // ConfigComponent.kt
  fun inject(configActivity: ConfigActivity)
```
`AppComponent` can implement this interface:
```
interface AppComponent : ConfigComponent { // AppComponent.kt
```
 We then need to introduce some way how to receive instance of `ConfigComponent`. One option is having a contract, that the `Application` object will implement the `HasAppComponent` interface. We can then get the instance of `AppComponent` and cast it to out `ConfigComponent`.
```
@Inject // ConfigActivity.kt
lateinit var mutableConfig: MutableConfig

override fun onCreate(savedInstanceState: Bundle?) {
  ((application as HasAppComponent).appComponent() as ConfigComponent).inject(this)
```

We can now launch the app, try to open `ConfigActivity` by the FAB and when unchecking the `bye_button` config, the "BYE BUTTON" will disappear from the main screen.

**We can now move into section [04-multibindings](https://github.com/jraska/Dagger-Codelab/tree/04-multibindings)**

### Optional Tasks
- Can you think about a different way how to pass instances of `AppComponent` into the `ConfigActivity`? How would you do that?
- Try to write an extension method `Activity.appComponent` and use it within `ConfigActivity` to reduce the number of casting.
