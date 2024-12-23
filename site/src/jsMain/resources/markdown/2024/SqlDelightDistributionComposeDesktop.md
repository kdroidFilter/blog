---
root: .components.layouts.BlogLayout
title: "Using SQLDelight with Distributions in Compose for Desktop"
description: "Learn how to enable SQLDelight functionality in Compose for Desktop applications by properly configuring distributions."
author: "Elyahou Hadass"
date: 2024-12-23
tags:
    - compose for desktop
    - sqldelight
    - kotlin
    - desktop development
category: "Compose"
---

## Using SQLDelight with Distributions in Compose for Desktop

When building Compose for Desktop applications, enabling SQLDelight to work seamlessly with native distributions can be tricky. In this article, we’ll share a simple tip to ensure SQLDelight is properly configured for your application’s distribution.

### Adding Required Modules for SQLDelight

To make SQLDelight function as expected in your Compose for Desktop application, you need to explicitly include the necessary `java.sql` module in your distribution setup. Below is the configuration to achieve this:

```kotlin
compose.desktop {
    application {
        mainClass = "com.kdroid.seforim.MainKt"

        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.kdroid.seforim"
            packageVersion = "1.0.0"
        }
    }
}
```

### Key Configuration Details

- **Modules Inclusion**: The line `modules("java.sql")` ensures that the `java.sql` module, required by SQLDelight, is included in your application’s runtime.
- **Target Formats**: The `targetFormats` configuration specifies the distribution formats for your application, such as `.dmg` for macOS, `.msi` for Windows, and `.deb` for Linux.
- **Application Metadata**: `packageName` and `packageVersion` define your application’s name and version, which are important for distribution packages.

### Why Is This Step Necessary?

Compose for Desktop applications are modular by design, and certain modules like `java.sql` are not included by default. SQLDelight relies on `java.sql` for database operations, and failing to include it can lead to runtime errors.

### Conclusion

By explicitly including the `java.sql` module in your Compose for Desktop distribution configuration, you can ensure that SQLDelight works smoothly in your application. This simple addition can save you a lot of debugging time and enhance the reliability of your desktop application.

Start using this tip today to streamline your development process and deliver a seamless user experience!

