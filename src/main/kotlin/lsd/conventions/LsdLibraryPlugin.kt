package lsd.conventions

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

class LsdLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply(JavaLibraryPlugin::class.java)

            repositories.add(repositories.mavenCentral())

            extensions.configure(JavaPluginExtension::class.java) {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            dependencies.apply {
                add("testImplementation", "org.assertj:assertj-core:3.27.3")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-api:5.13.4")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-engine:5.13.4")
            }

            tasks.withType(Test::class.java) {
                useJUnitPlatform()
                testLogging {
                    events = setOf(TestLogEvent.FAILED)
                    exceptionFormat = TestExceptionFormat.FULL
                }
            }
        }
    }
}
