plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "io.github.lsd-consulting"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.0.0")
    implementation("io.github.gradle-nexus:publish-plugin:2.0.0")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:4.0.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.4")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")
    implementation("org.jetbrains.kotlin:kotlin-allopen:2.2.0")
}

gradlePlugin {
    plugins {
        create("lsdLibrary") {
            id = "lsd.library"
            implementationClass = "lsd.conventions.LsdLibraryPlugin"
        }
        create("lsdKotlinLibrary") {
            id = "lsd.kotlin-library"
            implementationClass = "lsd.conventions.LsdKotlinLibraryPlugin"
        }
    }
}
