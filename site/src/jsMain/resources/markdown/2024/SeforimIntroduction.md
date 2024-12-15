---
root: .components.layouts.BlogLayout
title: "Introduction to K-Droid Seforim"
description: An overview of the K-Droid Seforim project, detailing its goals, technologies, features, and development process.
author: Elyahou Hadass
date: 2024-12-15
tags:
  - kotlin-multiplatform
  - sefaria
  - compose-desktop
  - compose-multiplatform
category: "Seforim"
---

# Introduction to Seforim

Hello everyone,

Today, I will introduce you to the **K-Droid Seforim** project.

## What is the project about?

The primary goal of the project is to utilize the **Sefaria** database to develop:

- **A PC/Mobile application** that works entirely offline.
- **A website** for online use. The web version will only allow browsing the database. It will not include integrated search functionality or synchronization features.

The website will be fully static, making it easy to host on **GitHub Pages** for a simple and accessible solution.

To learn more about **Sefaria**, you can visit the following links:

- Official website: [https://www.sefaria.org/](https://www.sefaria.org/)
- GitHub repository: [https://github.com/sefaria](https://github.com/sefaria)

The ultimate goal is to harness the full potential of **Kotlin Multiplatform (KMP)** to provide an optimal application regardless of the platform used. We will start by developing the PC version.

## Technologies Used

The program will be written entirely in **Kotlin**. Here are the technologies and tools that will be used:

- **Kotlin Multiplatform (KMP)** to share logic across different platforms.
- **Compose** as the foundation for the user interface (UI):
  - **Compose Multiplatform** for PC and iOS versions.
  - **Jetpack Compose** for the Android version.
  - **Kobweb** to develop the web version.
- **KosherKotlin** ([https://github.com/Sternbach-Software/KosherKotlin](https://github.com/Sternbach-Software/KosherKotlin)) for implementing Jewish calendar features.
- **Compose Rich Editor** ([https://github.com/MohamedRejeb/compose-rich-editor](https://github.com/MohamedRejeb/compose-rich-editor)) for writing and managing comments.

We will use a modular architecture based on **Clean Architecture (CA)** combined with **Model-View-ViewModel (MVVM)** in the **KMP** part to structure and organize the code effectively and maintainably.

For dependency injection, we will use **Koin**, and for network requests, **Ktor** will be our primary choice.

The program will be written entirely in **Kotlin**. Here are the technologies and tools that will be used:

- **Kotlin Multiplatform (KMP)** to share logic across different platforms.
- **Compose** as the foundation for the user interface (UI):
  - **Compose Multiplatform** for PC and iOS versions.
  - **Jetpack Compose** for the Android version.
  - **Kobweb** to develop the web version.

We will use a modular architecture based on **Clean Architecture (CA)** combined with **Model-View-ViewModel (MVVM)** in the **KMP** part to structure and organize the code effectively and maintainably.

For dependency injection, we will use **Koin**, and for network requests, **Ktor** will be our primary choice.

We will discuss these technologies in more detail during phase 3 of the project, but for the user interface:

- On **desktop**, we will use [Jewel](https://github.com/JetBrains/jewel).
- For **iOS**, we will prefer [Compose Cupertino](https://github.com/schott12521/compose-cupertino), although iOS development is not planned immediately. This could evolve in the future.
- On **Android**, we will adopt **Material3**.
- Finally, for the web version, we will use the **Silk** theme from [Kobweb](https://github.com/varabyte/kobweb/).

In the future, when the [Compose Fluent UI](https://github.com/Konyaco/compose-fluent-ui/) project is completed, it would be interesting to consider adapting the **desktop** version of the user interface to Fluent and allow the user to choose their preferred theme. Currently, this project is not stable and encounters serious performance issues, but these improvements should come gradually.

It is also worth following the evolution of the [Compose GTK Native](https://gitlab.com/opensavvy/ui/compose-gtk-native) project, which is still in its early stages but could become a viable option for the **desktop** version. However, this approach uses **Kotlin Native**, which would deprive the application of JVM performance for desktop. This would nevertheless be offset by a lighter application startup. It should be noted that, in this project, we also aim to ensure compatibility with iOS and JavaScript, which prevents us from using Java libraries in the application itself.

## Process Steps

The project will be divided into six major steps:

1. **Define the main features of the application**
2. **Database creation**
3. **User Interface (UI) development**
4. **Search engine design**
5. **Adding synchronization features**
6. **Application deployment**

## Main Features

Here are the main features the application should include:

- [ ] **Link sharing**: Users should be able to share a link to a specific book that redirects to the web version.

- [ ] **Update notifications**: The application will notify the user if an update is available for the app or the database.

- [ ] **Complete offline browsing** (Except web)
- [ ] **Structured display of comments**
- [ ] **Advanced search**:
  - [ ] Search for a specific book
  - [ ] Search for text within a book
  - [ ] Search across the entire library
- [ ] **Annotations and highlights**
- [ ] **Tab management**
- [ ] **Favorite saving**
- [ ] **Numerical calculations (Gematria)**
- [ ] **Measurement conversions**
- [ ] **Predefined structured modes**:
  - [ ] Chnayim Mikra Ehad Targoum
  - [ ] Tehilim Yomi
  - [ ] Daf Hayomi
  - [ ] Hafetz Haim Yomi
  - [ ] Hok Leyisrael
  - [ ] Houmach Rashi with the Haftara
  - [ ] Tur and its commentators
  - [ ] Choulhan Aroukh and its commentators
  - [ ] Rambam and its commentators
- [ ] **Dark mode**
- [ ] **Jewish calendar features**
- [ ] **Intelligent Siddur**
- [ ] **Option to print texts and annotations**
- [ ] **Recently opened books**
- [ ] **Automatic startup at system launch (Desktop)**
- [ ] **Ability to minimize the app to the System Tray (Desktop)**

