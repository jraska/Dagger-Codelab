
# Dagger-Codelab  
Codelab to teach and demonstrate usage of dependency injection with Dagger 2  
  
[![CircleCI](https://circleci.com/gh/jraska/Dagger-Codelab.svg?style=svg)](https://circleci.com/gh/jraska/Dagger-Codelab)  
  
# What you will learn  
- About dependency injection(DI) and Inversion of Control(IoC) 
  - Introduction to the problem, why it matters.  
  - Why we use DI frameworks - which problems they solve for us.  
  - Brief history of DI. Spring -> Guice -> Dagger 1 -> **Dagger 2**  
[Initial presentation slides](https://docs.google.com/presentation/d/1Yy6BdGQiZ8QQfT11Emwxs2F_bjAH4m9A8Ayz4luV2dw/edit?usp=sharing)  
- Basics of Dagger  
  - How to setup Dagger in your project.  
  - How to use `@Inject` annotation, craeting simple `@Component`  
  - Adding `@Module`. Discussion when to use modules and when `@Inject` annotation.  
  - Usage of simle `@Singleton` scope.  
  - Exploring Dagger generated source code and learning from it.  
- Wiring together with Android  
  - Where to create and hold instances of components.  
  - Injecting Activities, Injecting Fragments.  
  - Using `@BindsInstance` to bind objects we don't own.  
- Multi-module project setup  
  - How to compose modules together within `AppComponent`  
  - Injecting Activity/Fragment and other dependencies within separate module.  
  - Sharing instances with other modules.  
- Multibindings  
  - Composing `@IntoMap` and `@IntoSet`.  
  - Plugin based development within multi-module project.  
  - Discuss which problems can multibinding solve.  
- Instrumented Testing  
  - How to replace dependencies within `androidTest`.  
  - Using component dependency to replace dependencies in tests.  
  - How to assert on your analytic events.  
  
# Why this codelab?  
- Primariliy this should be a learnign resource for any engineer who wants to learn or recap Dagger usage.  
- The codelab format is chosen with the belief, that first hand experience leaves stronger memories and the skills are truly learned.  
- Dagger became more complex over years - making onboarding of new engineers harder.  
- Dagger became also misused or overused. New engineers see already brownfield and Dagger seems like one of the devils responsible for this leading to lower trust in the tool. Dagger can be almost invisible in your project.  
- We already forgot about the problems we had before Dagger like runtime DI resolution. It is important to be reminded about these problems.  
  
# How to use this Codelab  
- Codelab is split into few smaller sections - each of them can be run independently.  
- Each section has a branch with a format `{section number}-{area of focus}` e.g. `01-basics`. The branch with higher number is always the solution of the previous section. `master` branch is the final solution of present tasks.  
- You can run the codelab yourslef, by following the instructions within each sections.  
- In case of any question, please feel free to [create an issue](https://github.com/jraska/Dagger-Codelab/issues/new) in this repo.  
  
## Now let' start by moving to section you want  
- **[01-basics](https://github.com/jraska/Dagger-Codelab/tree/01-basics)**  
- **[02-wiring-with-android](https://github.com/jraska/Dagger-Codelab/tree/02-wiring-with-android)**  
- **[03-multiple-module-setup](https://github.com/jraska/Dagger-Codelab/tree/03-multiple-module-setup)**  
- **[04-multibindings](https://github.com/jraska/Dagger-Codelab/tree/04-multibindings)**  
- **[05-testing](https://github.com/jraska/Dagger-Codelab/tree/05-testing)**  
  
  
# References  
- [Android docs](https://developer.android.com/training/dependency-injection/dagger-basics),  
  - [Multi-Module setup](https://developer.android.com/training/dependency-injection/dagger-multi-module)  
- [Google Codelab](https://codelabs.developers.google.com/codelabs/android-dagger)  
- [Dagger docs](https://dagger.dev)  
  - [Android setup](https://dagger.dev/android) explains why we use field injection.  
  - [Official tutorial](https://dagger.dev/tutorial/)  
  - [Multibindings](https://dagger.dev/multibindings.html)  
  - [`@Binds`/`@Provides` discussion](https://dagger.dev/faq.html#what-do-i-do-instead)  
- [Gregory Kick - DAGGER 2 - A New Type of dependency injection (2014)](https://www.youtube.com/watch?v=oK_XtfXPkqw) - Recommended resource to understand the history of DI and why Dagger 2 exists.  
- [Gregory Kick - Dagger to on MCE^3 (2016)](https://www.youtube.com/watch?v=iwjXqRlEevg) - Introduction of main Dagger features as we know them now like Multibindings and the rationale behind them.  
- [Josef Raska - 3 Years of Happy Marriage With Dagger 2 (2018)](https://proandroiddev.com/3-years-of-happy-marriage-with-dagger-2-b1e1e0febaa7) - Recap of which problems Dagger solved and how it moved Android development forward, even if it might not seem obvious now.  
- [Jake Wharton - Helping Dagger help you (2018)](https://jakewharton.com/helping-dagger-help-you/) - Showcase how far you can go with Dagger features and improvements.
