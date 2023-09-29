<h1 align="center">Movie Library (Moly)</h1>

<p align="center">
Moly is a sample Android project using <a href="https://www.themoviedb.org/">The Movie DB</a> API based on MVVM architecture. It showcases the latest Android tech stacks with well-designed architecture and best practices.

</p>

## Features
* 100% Kotlin
* MVVM architecture
* Android Architecture Components and Jetpack.
* Kotlin Coroutines + Flow 
* Single activity pattern
* Version Catalog TOML

## Tech Stacks
* [Retrofit](http://square.github.io/retrofit/) + [OkHttp](http://square.github.io/okhttp/) - RESTful API and networking client.
* [Hilt](https://dagger.dev/hilt/) - Dependency injection.
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - A collections of libraries that help you design rebust, testable and maintainable apps.
    * [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - UI related data holder, lifecycle aware.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holder that notify views when underlying data changes.
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Handling lifecycles with lifecycle-aware component
    * [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Instance of a binding class contains direct references to all views that have an ID in the corresponding layout.
    * [Navigation component](https://developer.android.com/guide/navigation) - Fragment routing handler. 
    * [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
* [Coroutine](https://developer.android.com/kotlin/coroutines) Concurrency design pattern for asynchronous programming.
* [Flow](https://developer.android.com/kotlin/flow) Stream of value that returns from suspend function.
* [Coil](https://github.com/coil-kt/coil) - Image loading.
* [Timber](https://github.com/JakeWharton/timber) - Extensible API for logging.
* [Logger](https://github.com/orhanobut/logger) - Simple, pretty and powerful logger for android.
* [Gson](https://github.com/google/gson) - Converting JSON to Kotlin data class.
* [Glide](https://github.com/bumptech/glide) - Loading and caching images.
* [Glide Transformations](https://github.com/wasabeef/glide-transformations) - Providing image transformations for Glide.
* [YouTube Android Player](https://github.com/PierfrancescoSoffritti/android-youtube-player) - Playing YouTube video in app.
* [ExpandableTextView](https://github.com/yuzumone/ExpandableTextView) - TextView library that performs multiline ellipsize.
* [Klock](https://docs.korge.org/klock/) - Date & Time library for Multiplatform Kotlin 1.3.
* [Desugar](https://github.com/google/desugar_jdk_libs) - Simplified for use on older runtimes.
* [TOML](https://toml.io/en/) - Version catalog is a list of dependencies, represented as dependency coordinates, that a user can pick from when declaring dependencies in a build script.


## Architectures

We follow Google recommended [Guide to app architecture](https://developer.android.com/jetpack/guide) to structure our architecture based on MVVM, reactive UI using LiveData / Hilt observables and view binding.

* **View**: Activity/Fragment with UI-specific logics only.
* **ViewModel**: It keeps the logic away from View layer, provides data streams for UI and handle user interactions.
* **Model**: Repository pattern, data layers that provide interface to manipulate data from both the local and remote data sources. The local data sources will serve as [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth).

## Package Structures

```
dev.byto.moly           # Root Package
├── data                # For data modeling layer
│   ├── mapper          # Contains mapping functions to map DTOs(Data Transfer Objects)
│   ├── remote          # contains *Retrofit* components to fetch data from the network source
|   │   ├── api         # API Service
|   │   └── dto         # DTOs(Data Transfer Objects)
│   └── repository      # contains **implementations** of repository interfaces that are defined in the domain layera
|
├── di                  # Dependency injection modules
|
├── domain              # Domain layer is the central layer of the project
│   ├── model           # contains data classes that hold the data retrieved from the data layer to be used later  
│   │                     on in the presentation layer to expose it to UI
│   ├── repository      # contains repository **interfaces** to abstract the domain layer from the data layer
│   └── usecase         # contains use cases(interactors) that handle the business logic, which are reused by multiple ViewModels
│
├── ui                  # contains Activities & Fragments with their corresponding ViewModel classes
│   ├── adapter         # contains RecyclerView  adapter classes
│   ├── base            # Base ViewModel
│   └── screen          # Location screen Fragment and ViewModel
|   |   └── detail      # Detail screen Fragment and ViewModel
|   |   |   └── dialog  # Dialog open Youtube
|   |   ├── home        # Home screen Fragment and ViewModel
|   |   ├── list        # List screen Fragment and ViewModel
|   |   └── MainActivity# Single activity
|   └── UiState         # UI State response
├── utils               # Utility Classes / Kotlin extensions
└── MainApp             # Application

```

The project separated into three main layers:
- Data
- UI
- Domain

## API Key 🔑
You will need to provide developer key to fetch the data from TMDB API.
* Generate a new key (v3 auth) from [here](https://www.themoviedb.org/settings/api). Copy the key and go back to Android project.
* Define a constant `API_KEY` with the double quotes, it looks like

```kotlin
private const val API_KEY = "\"5423as******************655\""
```
* Add the key to build config in `./app/src/main/java/dev/byto/moly/di/NetworkModule.kt`:
* Perform gradle sync.

> **NOTE**: It's important to keep the double quotes for this value, since it's used as String type build config fields, the field name in quotes, the field value in escaped quotes additionally. If you're missing the double quotes, it will build fail.

## LICENSE

```
Copyright 2023 Toby Zulkarnain

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

