@file:Suppress("ConstPropertyName")

package helper


import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

/**
 * Retrieves the shared version catalog named "libs" from the project's extensions.
 *
 * This is commonly used to access dependencies and plugin coordinates defined in the
 * `libs.versions.toml` file, enabling consistent versioning across the project.
 *
 * @return the [VersionCatalog] named "libs"
 * @throws IllegalStateException if the "libs" version catalog is not found
 */
fun Project.getLibs(): VersionCatalog =
    extensions.findByType(VersionCatalogsExtension::class.java)?.named("libs")
        ?: error("Version catalog 'libs' not found")

object Libs {

    object Library {
        object KotlinX {
            const val Serialization = "kotlinx.serialization"
        }
    }

    object Plugin {
        const val AndroidApplication = "android.application"
        const val AndroidLibrary = "android.library"

        object Kotlin {
            const val Android = "kotlin.android"
            const val Serialization = "kotlin.serialization"
        }
    }
}