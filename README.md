
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

**We can now move into section  [05-testing](https://github.com/jraska/Dagger-Codelab/tree/05-testing)**

## Optional Tasks
- How would you solve the situation when you need to display configs sorted by alphabet?
- Add some more configs and see hwo they appear on screen. Adjust behaviour of the app based on them.
