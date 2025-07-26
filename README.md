# LSD Kotlin Conventions

A Gradle convention plugin for standardizing build configurations across LSD projects.

## Overview

This project provides Gradle convention plugins that encapsulate common build logic and dependencies used across LSD (Living Sequence Diagrams) projects, reducing boilerplate and ensuring consistency.

## Plugins

### `lsd.library`
Basic Java library conventions including:
- Java 17 compatibility
- Maven Central repository
- Common test dependencies (JUnit 5, AssertJ)
- Test configuration with proper logging

### `lsd.kotlin-library`
Kotlin library conventions (extends `lsd.library`) including:
- Kotlin JVM plugin with Java 17 toolchain
- Dokka for documentation generation
- JaCoCo for code coverage (version 0.8.12)
- Sources and Javadoc JAR generation
- Git hooks installation task

## Usage

### In your `settings.gradle` or `settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
}
```

### In your `build.gradle`:

```groovy
plugins {
    id 'lsd.kotlin-library' version '1.0.0'
}
```

### In your `build.gradle.kts`:

```kotlin
plugins {
    id("lsd.kotlin-library") version "1.0.0"
}
```

## Building

To build and publish to local Maven repository:

```bash
./gradlew publishToMavenLocal
```

## License

MIT License - see LICENSE file for details.
