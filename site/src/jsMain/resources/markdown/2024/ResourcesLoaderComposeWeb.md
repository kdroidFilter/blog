---
root: .components.layouts.BlogLayout
title: "Implementing a Loader in Compose Web: A Complete Guide"
description: Learn how to display a loading spinner in your Compose Web application while large resources are being loaded, ensuring a seamless user experience.
author: Elyahou Hadass
date: 2024-12-30
tags:
    - compose web
    - loader
    - kotlin
    - web development
category: "Compose"
---


## Displaying a Loader in Compose Web While Loading Large Resources

When developing a Compose Web application, ensuring a smooth user experience during resource loading is essential. Large resource files (10-20 MB) can cause noticeable delays, and leaving the screen blank can frustrate users. This guide demonstrates how to implement a loader to keep users engaged while the application initializes.

---


### Step 1: Create the HTML Structure

The loader will be displayed using a combination of HTML and CSS. Start by adding the following structure to your HTML file:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Compose Web App</title>
    <link rel="stylesheet" href="styles.css"/>
</head>
<body>
    <div id="loader">
        <div class="spinner"></div>
    </div>
    <div id="app"></div>
</body>

<script>
    // Function to hide the loader and show the app
    function appLoaded() {
        const loader = document.getElementById("loader");
        const app = document.getElementById("app");
        if (loader) loader.style.display = "none"; // Hide the loader
        if (app) app.style.display = "block";     // Show the app
        console.log("App is fully loaded!");
    }
</script>
<footer>
    <!-- The composeApp.js script is placed in the footer to ensure the loader displays as quickly as possible. -->
    <script type="application/javascript" src="composeApp.js"></script>
</footer>
</html>
```

---

### Step 2: Add CSS for the Loader

Define styles for the loader in your `styles.css` file. The loader will be a full-screen overlay with a spinning animation:

```css
/* Loader container: full screen, centered, and on top of everything */
#loader {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #ffffff; /* Background color */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999; /* Ensures the loader is above all other content */
}

/* Styling for the spinner */
.spinner {
    width: 60px;
    height: 60px;
    border: 8px solid #f3f3f3;        /* Gray ring */
    border-top: 8px solid #3498db;    /* Colored ring */
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

/* Rotation animation */
@keyframes spin {
    0%   { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

/* Main application, initially hidden */
#app {
    display: none;
}
```

---

### Step 3: Integrate Loader with Compose Web

Modify the `main` function in your Kotlin Compose Web code to notify the browser when the app is ready:

```kotlin
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body ?: return
    ComposeViewport(body) {
        notifyAppLoaded()
        LoadAppContent()
    }
}

external fun appLoaded()

fun notifyAppLoaded() {
    appLoaded() // Calls the JavaScript function appLoaded
}
```

Here:

- `notifyAppLoaded` invokes the JavaScript `appLoaded` function to hide the loader and show the app.
- `LoadAppContent` initializes your app's UI.

---

### Tips for Optimizing User Experience

1. **Preload Resources**: Use techniques like lazy loading for non-critical resources to reduce initial load time.
2. **Keep the Loader Lightweight**: A simple spinner or progress bar ensures quick rendering.
3. **Feedback**: Provide visual cues or messages (e.g., "Loading...") to reassure users.

---

By following these steps, you can enhance your Compose Web application's user experience by displaying a loader during resource-intensive initialization. This ensures users are engaged and informed while waiting for your app to fully load.

