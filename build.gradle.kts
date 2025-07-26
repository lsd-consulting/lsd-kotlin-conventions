plugins {
    `kotlin-dsl`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("com.palantir.git-version") version "4.0.0"
}

group = "io.github.lsd-consulting"
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion().replace(Regex("^v"), "")

logger.lifecycle("Version: $version")

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

    repositories {
        maven {
            name = "ossrh-staging-api"
            url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            credentials(PasswordCredentials::class)
        }
    }
}

signing {
    if (project.findProperty("signingKey") != null) {
        // Use in-memory ascii-armored keys
        val signingKey: String? = findProperty("signingKey") as String?
        val signingPassword: String? = findProperty("signingPassword") as String?
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    } else {
        // Use signing properties in ~/.gradle/gradle.properties
        sign(publishing.publications)
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
