plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("com.palantir.git-version") version "4.2.0"
}

group = "io.github.lsd-consulting"
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion().replace(Regex("^v"), "")

logger.lifecycle("Version: $version")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(17)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.1.0")
    implementation("org.jetbrains.dokka:javadoc-plugin:2.1.0")
    implementation("io.github.gradle-nexus:publish-plugin:2.0.0")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:4.2.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.5.7")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")
    implementation("org.jetbrains.kotlin:kotlin-allopen:2.2.21")
}

gradlePlugin {
    plugins {
        create("lsdLibrary") {
            id = "io.github.lsd-consulting.library"
            implementationClass = "io.github.lsd.consulting.conventions.LsdLibraryPlugin"
            displayName = "LSD Library Plugin"
            description = "Base conventions for LSD Java libraries"
        }
        create("lsdKotlinLibrary") {
            id = "io.github.lsd-consulting.kotlin-library"
            implementationClass = "io.github.lsd.consulting.conventions.LsdKotlinLibraryPlugin"
            displayName = "LSD Kotlin Library Plugin"
            description = "Extended conventions for LSD Kotlin libraries with Dokka and JaCoCo"
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("LSD Kotlin Conventions")
                description.set("Gradle convention plugins for LSD projects.")
                url.set("https://github.com/lsd-consulting/lsd-kotlin-conventions")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/lsd-consulting/lsd-kotlin-conventions/blob/main/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("nickmcdowall")
                        name.set("Nick")
                        email.set("nicholas.mcdowall@gmail.com")
                    }
                    developer {
                        id.set("lukaszgryzbon")
                        name.set("Lukasz")
                        email.set("lukasz.gryzbon@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git@github.com:lsd-consulting/lsd-kotlin-conventions.git")
                    developerConnection.set("scm:git:git@github.com:lsd-consulting/lsd-kotlin-conventions.git")
                    url.set("https://github.com/lsd-consulting/lsd-kotlin-conventions")
                }
            }
        }
    }
}


signing {
    val signingKey: String? = findProperty("signingKey") as String?
    val signingPassword: String? = findProperty("signingPassword") as String?
    val signingKeyId: String? = findProperty("signing.keyId") as String?
    
    if (signingKey != null && signingPassword != null) {
        // Use in-memory ascii-armored keys
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    } else if (signingKeyId != null && findProperty("signing.password") != null) {
        // Use signing properties in ~/.gradle/gradle.properties
        sign(publishing.publications)
    } else {
        logger.lifecycle("Signing disabled - no valid signing configuration found")
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
        }
    }
}
