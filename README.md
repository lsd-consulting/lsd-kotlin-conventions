[![semantic-release](https://img.shields.io/badge/semantic-release-e10079.svg?logo=semantic-release)](https://github.com/semantic-release/semantic-release)
[![CI](https://github.com/lsd-consulting/lsd-kotlin-conventions/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/lsd-consulting/lsd-kotlin-conventions/actions/workflows/ci.yml)
[![GitHub release](https://img.shields.io/github/release/lsd-consulting/lsd-kotlin-conventions)](https://github.com/lsd-consulting/lsd-kotlin-conventions/releases)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.lsd-consulting/lsd.kotlin-library.gradle.plugin.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.lsd-consulting%22%20AND%20a:%22io.github.lsd-consulting.kotlin-library.gradle.plugin%22)

# LSD Kotlin Conventions

Gradle convention plugins for LSD projects.

## Overview

This project provides Gradle convention plugins that encapsulate common build logic and dependencies used across LSD (Living Sequence Diagrams) projects, reducing boilerplate and ensuring consistency.

## Plugins

### `library`
Base plugin for Java libraries with:
- Java 17 toolchain
- Common test dependencies (JUnit 5, AssertJ)
- Test configuration with proper logging

### `kotlin-library`
Extends `library` with Kotlin-specific features:
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
    id('io.github.lsd-consulting.kotlin-library') version '1.1.4'
}
```

### For Java-only projects:

```kotlin
plugins {
    id('io.github.lsd-consulting.library') version '1.1.4'
}
```

## Building

To build and publish to local Maven repository:

```bash
./gradlew publishToMavenLocal
```

## License

MIT License - see LICENSE file for details.
