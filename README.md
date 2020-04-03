[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

# Dagger-Codelab - Section 4 - Multibindings

## What you will learn
- Multibindings
  - Composing @IntoMap and @IntoSet.
  - Plugin based development within multi-module project.
  - Discuss which problems can multibinding solve.

# Section 4: Multibindings - Instructions
In this module, we will be performing mainly refactoring using Dagger Multibindings. We can use our `AppTest` to verify our changes are correct.

## Task 1: Use MultibindInMemoryConfig istead of InMemoryConfig
Both of these implement `MutableConfig` interface and therefore the only step is to instruct Dagger to bind different implementation.

```
// ConfigModule.kt
  fun remoteConfig(config: MultibindInMemoryConfig): MutableConfig = config
```


