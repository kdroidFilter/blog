---
root: .components.layouts.BlogLayout
title: "Setting Up a Splash Screen in Compose for Desktop: A Step-by-Step Guide"
description: Learn how to set up a visually appealing splash screen in your Compose for Desktop project using a shared codebase configuration.
author: Elyahou Hadass
date: 2024-12-13
tags:
  - compose desktop
  - splash screen
  - kotlin
category: "Compose"
---

A splash screen is an excellent way to provide users with a visually engaging placeholder while your Compose for Desktop application initializes. This guide will show you how to set up a splash screen in your Compose for Desktop project using the jvmArgs configuration in your build.gradle.kts file.

## Prerequisites

- A Compose for Desktop project set up.
- A splash screen image file (`splash.png`) ready in the assets folder.

## Steps to Add a Splash Screen

### 1. Organize Your Assets Folder
Ensure you have an assets folder in your project root directory. Place the `splash.png` file inside this folder. The path should look like:

```
/project-root/assets/splash.png
```

### 2. Configure `build.gradle.kts`
Add the `nativeDistributions` configuration in your `build.gradle.kts` file to set up the splash screen. Here's the updated configuration:

```kotlin
compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            appResourcesRootDir.set(project.layout.projectDirectory.dir("assets"))
            jvmArgs += "-splash:${'$'}APPDIR/resources/splash.png"
        }
    }
}
```

#### Explanation of the Configuration

- **`mainClass`**: Specifies the entry point of your application.
- **`nativeDistributions`**: Indicates where your application resources are located. By setting the `appResourcesRootDir` to the `assets` directory, your resources (like `splash.png`) are included in the build.
- **`jvmArgs`**: Configures JVM arguments to display the splash screen. The `-splash` option points to the splash image location.

### 3. Run Your Application
Build and run your Compose for Desktop application. When launching, the splash screen will display until your app's main window is fully initialized.

## Tips for an Engaging Splash Screen

- **Keep it Simple**: Use a minimalistic design for the splash screen to ensure quick loading.
- **Resolution**: Ensure the image has a resolution that fits common screen sizes.
- **Branding**: Incorporate your app's logo or theme to make a memorable first impression.

With these steps, your Compose for Desktop application will feature a professional splash screen, enhancing the user experience while your application starts.

