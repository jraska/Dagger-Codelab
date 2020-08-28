
[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)

# Dagger-Codelab - Section 1 - Basics

## What you will learn
- Basics of Dagger
  - How to setup Dagger in your project.
  - How to use `@Inject` annotation, creating simple `@Component`
  - Adding `@Module`. Discussion when to use modules and when `@Inject` annotation.
  - Usage of simple `@Singleton` scope.
  - Exploring Dagger generated source code and learning from it.
  - 
# Goal
- Generate Dagger implementation of `AnalyticsComponent` and pass `AnalyticsComponentTest` whilst using it.

# Section 1: Basics - Instructions

First please have a look into the `:core` module and into `com.jraska.dagger.codelab.core.analytics` package. Our first exercise will take place here.

You can see interface `EventAnalytics`, which can model a way how our application publishes `AnalyticsEvent` objects. Its implementation `LibraryEventAnalytics` uses `AnalyticsFilter` to filter too long events and has `.lib.AnalyticsLibrary` to model some external dependency.
You can imagine Google Analytics, Firebase or any other external analytics framework in place of `.lib.AnalyticsLibrary`

<img width="350" alt="Screenshot 2020-03-30 at 00 09 20" src="https://user-images.githubusercontent.com/6277721/77862570-c4862d80-721c-11ea-8685-01686ad6524a.png">

There is also `AnalyticsComponent`, which will be our representation of dependency graph and will provide us instances. You can see manual composing of these dependencies within `AnalyticsComponentTest` in `test` sources.
We will be using `AnalyticsComponentTest` to test our code within this section.

## Task 1: Adding Dagger 2 dependency.
To be even able to use Dagger 2, we need to add it as a dependency. You can check [Dagger releases]([https://github.com/google/dagger/releases](https://github.com/google/dagger/releases)) for latest version.
```
dependencies {  // core/build.gradle
// ...
    implementation 'com.google.dagger:dagger:2.28.3'
    kapt 'com.google.dagger:dagger-compiler:2.28.3'
// ...
}

```
 `implementation` is here to see the API and internal code from Dagger, `kapt` dependency pulls in the Dagger annotation processor.

## Task 2: Adding @Inject annotation
Dagger can satisfy dependencies of any instance, which declares its constructor with `@javax.inject.Inject` annotation. We can start by adding a `@Inject` constructor to `LibraryEventAnalytics`
```
class LibraryEventAnalytics @Inject constructor( // LibraryEventAnalytics.kt
```
This can be enough to actually see Dagger in action. We need to now make our `AnalyticsComponent` a Dagger component. This can be easily done by annotating it with `@dagger.Component` annotation.

```
@Component
interface AnalyticsComponent { // AnalyticsComponent.kt
```

We can try to build our project now, but we receive error message:
```
[Dagger/MissingBinding] com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary cannot be provided without an @Inject constructor or an @Provides-annotated method.
public abstract interface AnalyticsComponent {
                ^
      com.jraska.dagger.codelab.core.analytics.lib.AnalyticsLibrary is injected at
          com.jraska.dagger.codelab.core.analytics.LibraryEventAnalytics(arg0, …)
      com.jraska.dagger.codelab.core.analytics.LibraryEventAnalytics is provided at
          com.jraska.dagger.codelab.core.analytics.di.AnalyticsComponent.eventAnalytics()
```
This part is important, because we will face this very often with Dagger and it is crucial to understand those messages. There is some binding missing and by checking the message we can read that Dagger cannot see `AnalyticsLibrary`, which is natural, because we didn't tell Dagger how to create its instances.
The error message also shows the path, where the `AnalyticsLibrary` requested to help us find the problem.
Current situation from Daggers point of view looks now like this:

<img width="350" alt="Screenshot 2020-03-30 at 00 09 34" src="https://user-images.githubusercontent.com/6277721/77862697-71f94100-721d-11ea-9270-6bc303c76c3c.png">

`AnalyticsLibrary` here is simulating a library we don't own and therefore we cannot just add `@Inject` annotation to its constructor. Instead we need to create `@dagger.Module`.

## Task 3: Adding a module
We have already prepared class `AnalyticsModule`. To make it a Dagger module, we need to add `@Module` annotation to its declaration:
```
@Module
object AnalyticsModule { // AnalyticsModule.kt
```
`AnalyticsComponent` needs to be instructed to consider this module in its graph. Annotation `@dagger.Component` has a `dependencies` property. where we can list all our classes, which happen to be a module.

```
@Component(modules = [AnalyticsModule::class])
interface AnalyticsComponent { // AnalyticsComponent.kt
```

## Task 4: Adding @Provides method
Now we have our `AnalyticsModule` and we can start adding bindings to it. We can tell Dagger how to get instances of `AnalyticsLibrary` like this:

```
@Provides // AnalyticsModule.kt
fun provideAnalyticsLibrary(): AnalyticsLibrary {
  return AnalyticsLibrary()
}
```

Now we can try to compile again and we receive another error message, telling us that we still miss `AnalyticsFilter` in our component graph.
<img width="450" alt="Screenshot 2020-03-30 at 00 42 37" src="https://user-images.githubusercontent.com/6277721/77862997-6a3a9c00-721f-11ea-90e0-ee028125d6df.png">

We can solve this by adding `@Inject constructor`to it:
```
class AnalyticsFilter @Inject constructor() { // AnalyticsFilter.kt
```
With this, our code will finally build and Dagger generates the component implementation.
<img width="450" alt="Screenshot 2020-03-30 at 00 42 55" src="https://user-images.githubusercontent.com/6277721/77863018-8dfde200-721f-11ea-9b98-2923bfcde6df.png">

## Task 5: Using generated DaggerAnalyticsComponent
Now we can look again into `AnalyticsComponentTest` . We can replace manual creation of dependencies to usage of generated `DaggerAnalyticsComponent`.
```
val analyticsComponent = DaggerAnalyticsComponent.create() // AnalyticsComponentTest.kt
```
Test will now compile and we will see a failure of test. The reason now is that method `eventAnalytics()` always creates a new instance of `LibraryEventAnalytics`. We can look at the generated `DaggerAnalyticsComponent` where we can see the `new` call for each `eventAnalytics`.
To change this behaviour, we will need a scope.

## Task 6: Using Singleton scope
Using a scope means first marking the component as scoped. This is done by annotating the `AnalyticsComponent` with `@javax.inject.Singleton` annotation.

```
@Singleton // AnalyticsComponent.kt
@Component(modules = [AnalyticsModule::class])
```
We also need to mark the `provideAnalyticsLibrary` as being scoped to `@Singleton`.
```
@Provides // AnalyticsModule.kt
@Singleton
fun provideAnalyticsLibrary(): AnalyticsLibrary {
```

`AnalyticsComponentTest` will now pass and there will be only one instance of `AnalyticsLibrary` created. We can check `DaggerAnalyticsComponent` how it is done. Dagger uses `DoubleCheck` implementation as an instance holder.

## Task 7: Providing an implementation to an interface
`AnalyticsComponent` now provides instances of `LibraryEventAnalytics`, but it might be better to hide this implementation and use the interface `EventAnalytics` instead.
```
interface AnalyticsComponent {  // AnalyticsComponent.kt
  fun eventAnalytics(): EventAnalytics
```
If we try to run our `AnalyticsComponentTest`, Dagger will again throw an error telling
```
EventAnalytics cannot be provided without an @Provides-annotated method
```
From Dagger's perspective it looks now like this:

<img width="559" alt="Screenshot 2020-03-30 at 19 49 27" src="https://user-images.githubusercontent.com/6277721/77944639-94dc3180-72bf-11ea-9536-e9742739a61c.png">

Even if `LibraryEventAnalytics` implements `EventAnalytics` interface, we need to explicitly tell Dagger to use `LibraryEventAnalytics` in case someone requests `EventAnalytics`  instance. One option how to do it is:
```
@Provides  // AnalyticsModule.kt
fun provideEventAnalytics(implementation: LibraryEventAnalytics): EventAnalytics {
  return implementation
}
```

To fix our test, we need to cheat a bit and cast it into `LibraryEventAnalytics`
```
// AnalyticsComponentTest.kt
val analyticsReported = (analyticsComponent.eventAnalytics() as
LibraryEventAnalytics).library.inMemoryData
```

**We can now move into section [02-wiring-with-android](https://github.com/jraska/Dagger-Codelab/tree/02-wiring-with-android)**

## Optional tasks
- Remove `@Singleton` scope from `AnalyticsComponent` and see what happens.
- Method `provideEventAnalytics` is not the most effective way how to bind implementation to interface. You can use `@Binds` annotation and nested interface `AnalyticsModule.Declarations` as [recommended by Dagger]([https://dagger.dev/faq.html#what-do-i-do-instead](https://dagger.dev/faq.html#what-do-i-do-instead)).
- We are now still creating unnecessary objects, will you figure out which ones?
