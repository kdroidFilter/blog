---
root: .components.layouts.BlogLayout
title: "How to Publish a KMP Library to Maven Central Without a Mac"
description: A step-by-step guide to publishing a Kotlin Multiplatform (KMP) library compatible with iOS and macOS to Maven Central, even without owning a Mac.
author: Elyahou Hadass
date: 2025-01-03
category: "kmp"
---

Publishing a Kotlin Multiplatform (KMP) library that supports iOS and macOS to Maven Central can be done efficiently, even without access to a Mac. This tutorial outlines the process using the `vanniktech-maven-publish` plugin on a Linux machine. The steps are adaptable to Windows as well.

## Prerequisites

Before proceeding, ensure you have the following:

1. **Sonatype Account**: A valid account on [Sonatype Central Repository](https://central.sonatype.com/).
2. **Validated Namespace**: A namespace approved by Sonatype.
3. **GPG Key**: A GPG key for signing your artifacts.
4. **Maven Central Credentials**: Your username and password (or token) for Maven Central.
5. **GPG Key Password**: The passphrase for your GPG key.

## Step 1: Configure the `vanniktech-maven-publish` Plugin

Add the plugin to your `libs.versions.toml` file:

```toml
[versions]
vanniktech = "0.30.0"

[plugins]
vanniktech-maven-publish = { id = "com.vanniktech.maven.publish", version.ref = "vanniktech" }
```

Update your `root build.gradle.kts`:

```kotlin
alias(libs.plugins.vanniktech.maven.publish).apply(false)
```

In the `build.gradle.kts` file of your library, apply the plugin:

```kotlin
plugins {
    alias(libs.plugins.vanniktech.maven.publish)
}
```

## Step 2: Configure Maven Publishing

Set up your library configuration in the `build.gradle.kts` file:

```kotlin
mavenPublishing {
    coordinates(
        groupId = "io.github.kdroidfilter",
        artifactId = "kmplog",
        version = appVersion
    )

    pom {
        name.set("Kmp RealTime Logger")
        description.set("KMP RealTime Logger is a Kotlin Multiplatform logging library that replicates the Android Log API for use across all major platforms. It supports configurable log levels, throwable logging, and real-time log broadcasting to devices on the same local network, making debugging easier and more efficient.")
        inceptionYear.set("2024")
        url.set("https://github.com/kdroidFilter/KmpRealTimeLogger")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("kdroidfilter")
                name.set("Elyahou Hadass")
                email.set("elyahou.hadass@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/kdroidFilter/KmpRealTimeLogger")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
```

## Step 3: Export and Configure the GPG Key

Convert your GPG key to Base64 format:

```bash
gpg --export-secret-keys --armor <key-id> | grep -v '\-\-' | grep -v '^=\.' | tr -d '\n' > base64key.txt
```

Create a secret in your GitHub repository for the Base64 key:

1. Go to **Settings > Secrets and Variables > New repository secret**.
2. Add a secret named `SIGNINGINMEMORYKEY` and paste the Base64 key.

Add these additional secrets:

- **SIGNINGKEYID**: The ID of your GPG key.
- **SIGNINGPASSWORD**: The password for your GPG key.
- **MAVENCENTRALUSERNAME**: The username of your Sonatype token.
- **MAVENCENTRALPASSWORD**: The password of your Sonatype token.

## Step 4: Create the GitHub Actions Workflow

Create a file at `.github/workflows/publish-on-maven-central.yml` with the following content:

```yaml
name: Publish to Maven Central

on:
  push:
    tags:
      - 'v*'

jobs:
  publish:
    runs-on: macos-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Set up Publish to Maven Central
        run: ./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
        env:
          ORG_GRADLE_PROJECT_mavenCentralUsername: ${{ secrets.MAVENCENTRALUSERNAME }}
          ORG_GRADLE_PROJECT_mavenCentralPassword: ${{ secrets.MAVENCENTRALPASSWORD }}
          ORG_GRADLE_PROJECT_signingInMemoryKey: ${{ secrets.SIGNINGINMEMORYKEY }}
          ORG_GRADLE_PROJECT_signingInMemoryKeyId: ${{ secrets.SIGNINGKEYID }}
          ORG_GRADLE_PROJECT_signingInMemoryKeyPassword: ${{ secrets.SIGNINGPASSWORD }}
```

## Step 5: Publish Your Library

To publish the library:

1. Create a Git tag starting with `v` (e.g., `v1.0.0`).
2. Push the tag to GitHub.

GitHub Actions will automatically upload your library to Maven Central using a macOS runner.

---

With these steps, you can publish your KMP library seamlessly to Maven Central without owning a Mac. The workflow ensures compatibility with iOS and macOS and leverages GitHub Actions to streamline the process.

