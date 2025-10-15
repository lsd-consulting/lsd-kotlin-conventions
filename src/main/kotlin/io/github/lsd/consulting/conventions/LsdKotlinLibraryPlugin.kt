package io.github.lsd.consulting.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class LsdKotlinLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply(LsdLibraryPlugin::class.java)
            plugins.apply(KotlinPluginWrapper::class.java)
            plugins.apply("org.jetbrains.dokka-javadoc")
            plugins.apply(JacocoPlugin::class.java)

            repositories.add(repositories.mavenLocal())

            extensions.configure(KotlinJvmProjectExtension::class.java) {
                jvmToolchain(17)
            }

            // Configure modern Kotlin compiler options for Gradle 8.x+
            tasks.withType(KotlinCompile::class.java) {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            // JaCoCo configuration
            extensions.configure(JacocoPluginExtension::class.java) {
                toolVersion = "0.8.12"
            }

            tasks.named("jacocoTestReport", JacocoReport::class.java) {
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    html.outputLocation.set(layout.buildDirectory.dir("reports/coverage"))
                }
            }

            tasks.named("test", Test::class.java) {
                finalizedBy(tasks.named("jacocoTestReport"))
            }

            // Source and javadoc jars
            extensions.configure(JavaPluginExtension::class.java) {
                withSourcesJar()
            }

            tasks.register("javadocJar", Jar::class.java) {
                dependsOn(tasks.named("dokkaGeneratePublicationJavadoc"))
                archiveClassifier.set("javadoc")
                from(layout.buildDirectory.dir("dokka/javadoc"))
            }

            // Git hooks task
            tasks.register("installGitHooks") {
                shouldRunAfter("clean")
                doLast {
                    println("-- Configuring git to use .githooks --")
                    project.exec {
                        commandLine("git", "config", "core.hooksPath", ".githooks")
                    }
                }
            }
        }
    }
}
