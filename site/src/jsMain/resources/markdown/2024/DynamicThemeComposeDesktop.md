---
root: .components.layouts.BlogLayout
title: "Dynamic Theme Switching in Compose for Desktop"
description: "Learn how to enable automatic dark and light theme switching in Compose for Desktop applications based on the operating system settings."
author: "Elyahou Hadass"
date: 2024-12-14
tags:
    - compose for desktop
    - dark theme
    - light theme
    - kotlin
    - desktop development
category: "Compose"
---

## Dynamic Theme Switching in Compose for Desktop

One of the great features of modern applications is the ability to automatically adapt to the operating system’s dark or light theme. In this article, we will explore how to achieve this functionality in a **Compose for Desktop** application using the [jSystemThemeDetector](https://github.com/Dansoftowner/jSystemThemeDetector) library.

### Setting Up Theme Detection

To dynamically adjust your Compose for Desktop application’s theme, we will use a simple utility class, `DarkModeDetector`, which leverages `jSystemThemeDetector`. This library efficiently detects the current theme (dark or light) and allows you to listen for theme changes in real-time.

### Implementing DarkModeDetector

Here is the implementation of the `DarkModeDetector` object:

```kotlin
import com.jthemedetecor.OsThemeDetector

object DarkModeDetector {
    private val detector = OsThemeDetector.getDetector()

    val isDarkThemeUsed: Boolean
        get() = detector.isDark

    fun registerListener(listener: (Boolean) -> Unit) {
        detector.registerListener { isDark -> listener(isDark) }
    }
}
```

### Using DarkModeDetector in Compose

You can integrate `DarkModeDetector` into your Compose application to dynamically switch between dark and light themes. Here’s how to do it:

```kotlin
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    var isDarkTheme by remember { mutableStateOf(DarkModeDetector.isDarkThemeUsed) }

    // Register the listener for theme changes
    DarkModeDetector.registerListener { isDarkTheme = it }

    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme(
            colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
        ) {
            // Your app content goes here
            MyApp()
        }
    }
}


```

### Key Features of This Approach

- **Real-Time Theme Switching**: Automatically updates the UI when the operating system theme changes.
- **Seamless Integration**: The `jSystemThemeDetector` library handles the heavy lifting, providing a simple API for detecting and responding to theme changes.
- **Compose-Friendly**: Uses `remember` and `mutableStateOf` to ensure that Compose reacts to state changes.

### Dependency Setup

To use the `jSystemThemeDetector` library, add the following dependency to your Gradle file:

**Kotlin DSL:**

```kotlin
implementation("com.github.Dansoftowner:jSystemThemeDetector:3.8")
```

### Why Use jSystemThemeDetector?

The `jSystemThemeDetector` library provides a robust and cross-platform way to detect the operating system’s theme. It works seamlessly across Windows, macOS, and Linux, making it an ideal choice for Compose for Desktop applications.

### Conclusion

With just a few lines of code, you can make your Compose for Desktop application adapt dynamically to the operating system’s theme settings. This improves the user experience and makes your app feel more integrated with the host OS.

For more details, check out the [jSystemThemeDetector GitHub repository](https://github.com/Dansoftowner/jSystemThemeDetector) and start enhancing your desktop application today!

