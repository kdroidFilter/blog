---
root: .components.layouts.BlogLayout
title: "Using an npm Library in a Compose Web (WASM) Project"
description: Learn how to integrate and use an npm library in a Compose Web project based on WebAssembly with this simple example.
author: Elyahou Hadass
date: 2024-12-31
tags:
  - compose web
  - wasm
  - kotlin
  - npm
category: "Compose"
---

Using npm libraries in a Compose Web (WASM) project might seem daunting at first, but it's a straightforward process once you understand the steps. In this article, we'll detail how to use an npm library in a Compose Web project, using **mathjs**, a popular library for mathematical calculations, as an example.


## Adding an npm Dependency in `build.gradle.kts`

Start by adding the npm library to your project's dependencies. In the `build.gradle.kts` file, add the following configuration:

```kotlin
implementation(npm("mathjs", "14.0.1"))
```

This indicates that you want to include version 14.0.1 of the `mathjs` library.

## Updating the Yarn Lock File

Once the dependency is added, run the following command to generate or update the `yarn.lock` file:

```bash
./gradlew kotlinUpgradeYarnLock
```

This command ensures that npm dependencies are properly synchronized with your Kotlin/JS project.

## Mapping npm Library Functions to Kotlin

To use `mathjs` functions, you need to create a Kotlin file that imports and maps the JavaScript functions to Kotlin functions. Create a new file, for example `MathJS.kt`, and add the following content:

```kotlin
@file:JsModule("mathjs")

package example

external fun round(value: Double, decimals: Int): Double
external fun atan2(y: Double, x: Double): Double
external fun log(value: Double, base: Double = definedExternally): Double
```

- **`@file:JsModule`**: Specifies the npm module to import.
- **`external fun`**: Maps JavaScript functions to Kotlin functions.

## Example Usage

Once the mapping is complete, you can use the `mathjs` functions as native Kotlin functions. Here's a simple example:

```kotlin
fun main() {
    val roundedValue = round(2.718, 3)
    println("Rounded value: $roundedValue")

    val angle = atan2(3.0, -3.0) / kotlin.math.PI
    println("Angle (in fractions of pi): $angle")

    val logarithm = log(10000.0, 10.0)
    println("Logarithm: $logarithm")
}
```

#### Expected Output

When you run this code, you should see output similar to the following:

```
Rounded value: 2.718
Angle (in fractions of pi): 0.75
Logarithm: 4.0
```

## Practical Tips

- **Check Types**: Ensure that the data types between Kotlin and JavaScript are compatible.
- **Documentation**: Refer to the official documentation of the npm library to learn more about the available functions.
- **Debugging**: In case of errors, use the browser's developer tools to inspect the behavior of the JS modules.

With these steps, you can easily integrate and use npm libraries in your Compose Web (WASM) projects, expanding the possibilities of your application.

