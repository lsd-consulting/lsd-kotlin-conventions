# LSD Kotlin Conventions

Gradle convention plugins for LSD projects.

## Overview

This project provides Gradle convention plugins that encapsulate common build logic and dependencies used across LSD (Living Sequence Diagrams) projects, reducing boilerplate and ensuring consistency.

## Plugins

### `lsd.library`
Base plugin for Java libraries with:
- Java 17 toolchain
- Maven Central publishing
- Common test dependencies (JUnit 5, AssertJ)
- Test configuration with proper logging

### `lsd.kotlin-library`
Extends `lsd.library` with Kotlin-specific features:
- Kotlin JVM plugin with Java 17 toolchain
- Dokka documentation generation
- JaCoCo test coverage
- Sources and Javadoc JAR generation
- Git hooks installation task

## Usage

### In your `settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
```

### In your `build.gradle.kts`:

```kotlin
plugins {
    id("lsd.kotlin-library") version "1.0.0"
}
```

### For Java-only projects:

```kotlin
plugins {
    id("lsd.library") version "1.0.0"
}
```

## Building

To build and publish to local Maven repository:

```bash
./gradlew publishToMavenLocal
```

## License

MIT License - see LICENSE file for details.
