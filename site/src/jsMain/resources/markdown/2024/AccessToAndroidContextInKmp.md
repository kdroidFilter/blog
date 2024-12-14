---
root: .components.layouts.BlogLayout
title: "Best Practices for Accessing Application Context in Kotlin Multiplatform Projects"
description: "Explore two practical methods for accessing the application context in Kotlin Multiplatform projects."
author: "Elyahou Hadass"
date: 2024-12-14
tags:
  - kotlin multiplatform
  - android
  - application context
category: "Android"
---

## Best Practices for Accessing Application Context in Kotlin Multiplatform Projects

When working with Kotlin Multiplatform (KMP), managing and accessing the application context can be tricky but crucial for writing scalable and maintainable code. Below, I’ll explore two methods to handle application context effectively, one using a custom `Initializer` and the other leveraging a utility library called **Android Context Provider**.

### Method 1: Using `internal lateinit var` and Initializer

This approach involves defining a late-initialized application context that is set during application startup. Here’s the implementation:

```kotlin
internal lateinit var applicationContext: Context
    private set

internal data object ContextProviderInitializer

class ContextProvider : Initializer<ContextProviderInitializer> {
    override fun create(context: Context): ContextProviderInitializer {
        applicationContext = context.applicationContext
        return ContextProviderInitializer
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
```

#### Advantages

- **Simplicity**: This method uses native tools without introducing external dependencies.
- **Control**: You have complete control over when and how the context is initialized.

#### Considerations

- **Manual Setup**: Requires you to set up an initializer explicitly in your project.
- **Risk of Late Initialization Issues**: Accessing the `applicationContext` before initialization will cause runtime crashes.

### Method 2: Using **Android Context Provider**

For a more streamlined and robust solution, consider using the **Android Context Provider** library. It simplifies context management in Android projects by providing global access to the application context.

#### Installation

The library is hosted on Maven Central. Add it to your project with the following:

**Kotlin DSL:**

```kotlin
implementation("io.github.kdroidfilter:androidcontextprovider:1.0.1")
```


#### Usage

The library automatically initializes itself during application startup using a `ContentProvider`. To retrieve the application context, simply use the following code:

**Kotlin:**

```kotlin
val context = ContextProvider.getContext()
// Use the context as needed
```

For more details, visit the [GitHub repository](https://github.com/kdroidFilter/AndroidContextProvider).

#### Important Notes

- **Automatic Initialization**: Thanks to the `ContentProvider` mechanism, manual setup is not required.
- **Error Handling**: An `IllegalStateException` is thrown if you attempt to access the context before initialization, though this is rare due to the automatic setup.

### Conclusion

Both methods provide practical solutions for managing application context in KMP projects. If you prefer simplicity and control, the manual initializer approach is a great choice. However, if you value ease of integration and reduced boilerplate, the **Android Context Provider** library is an excellent alternative.

Whichever method you choose, ensure that your application’s architecture aligns with your team’s preferences and the project’s requirements.

