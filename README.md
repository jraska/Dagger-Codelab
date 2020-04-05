

[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

# Dagger-Codelab - Section 4 - Multibindings

## What you will learn
- Multibindings
  - Composing @IntoMap and @IntoSet.
  - Plugin based development within multi-module project.
  - Discuss which problems can multibinding solve.

# Section 4: Multibindings - Instructions
In this module, we will be performing mainly refactoring using Dagger Multibindings. We can use our `AppTest` to verify our changes are correct.

## Task 1: Use MultibindInMemoryConfig instead of InMemoryConfig
Both of `MultibindInMemoryConfig` and  `InMemoryConfig` implement `MutableConfig` interface and therefore the only step is to instruct Dagger to bind different implementation.

```
// ConfigModule.kt
  fun remoteConfig(config: MultibindInMemoryConfig): MutableConfig = config
```
The project will now not build with Dagger error message:
```
[Dagger/MissingBinding] java.util.Map<java.lang.String,java.lang.Boolean> cannot be provided without an @Provides-annotated method.
...
```
Because `MultibindInMemoryConfig` expects the multi-bound `Map<String, Boolean>`.
This happens if we try to use multibinding and there is not a single @Provides method contributing to the Map.

## Task 2: Bind bye_button config
`bye_button` config was previously hardcoded within `InMemoryConfig` implementation, now can add it into `Map<String, Boolean>` by the following:
```
@Provides // ConfigModule.kt
@IntoMap
@StringKey(CONFIG_BYE_BUTTON)
fun byeButtonConfig() = true
```
Let's run `AppTest` again. It should pass this time.

## Task 3: Add ExpensiveConfig binding
We can now bind one more config, which is expensive to get value - we can think about it like a config which requires a call to backend.
```
@Provides // ConfigModule.kt
@IntoMap
@StringKey(ExpensiveConfig.KEY)
fun expensiveConfig() = ExpensiveConfig.value
```
We have now new config, but because it takes a long time to get the boolean value, we harm application startup time by this.

## Task 4: Initialise ExpensiveConfig
To mitigate the long delay to initialise, we can `@IntoSet` binding and run the initialisation in application `onCreate`.
```
@Provides // ConfigModule.kt
@IntoSet
fun expensiveConfigInitializer(): OnAppCreate = ExpensiveConfig.Initializer
```

```
// AppComponent.kt
fun onAppCreateActions(): Set<OnAppCreate>
```

```
override fun onCreate() {  // DaggerApp.kt
  super.onCreate()
  appComponent.onAppCreateActions().forEach { it.onCreate() }
}
```
We can now run the app again. The delay will unfortunately still exist at the app startup, because `MainActivity` injects `RemoteConfig`.

## Task 5: Use Provider to obtain injected values lazily
Dagger by design can always inject both `Dependency` and also `Provider<Dependency>`. Same applies fro multibindings.
We can request ` Map<String, Provider<Boolean>>` and Dagger will know how to inject it.
```
class MultibindInMemoryConfig @Inject constructor(  // MultibindInMemoryConfig.kt
  val initialState: Map<String, @JvmSuppressWildcards Provider<Boolean>>
...
.withDefault { initialState[it]?.get() ?: false }
```

Like this the config will be accessed only when `ConfigActivity` launches and enumerates all config values.

**We can now move into section  [05-testing](https://github.com/jraska/Dagger-Codelab/tree/05-testing)**

## Optional Tasks
- How would you solve the situation when you need to display configs sorted by alphabet?
- Add some more configs and see how they appear on screen. Adjust behaviour of the app based on them.
