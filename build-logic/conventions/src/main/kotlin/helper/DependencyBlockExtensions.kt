package helper

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * Adds an `api` dependency to the given library from the version catalog.
 *
 * This is useful in modular projects where you want to expose a library
 * to downstream modules while referencing it via its version catalog alias.
 *
 * Example usage:
 * ```
 * dependencies {
 *     api(libs, "koin.core")
 * }
 * ```
 *
 * @param libs The [VersionCatalog] containing the dependency definitions
 * @param libraryName The alias of the library as defined in `libs.versions.toml`
 */
fun DependencyHandler.api(libs: VersionCatalog, libraryName: String) {
    add("api", libs.findLibrary(libraryName).get().get())
}

/**
 * Adds an `implementation` dependency to the given library from the version catalog.
 *
 * This is used when the library is only needed internally within the module
 * and does not need to be exposed to consumers.
 *
 * Example usage:
 * ```
 * dependencies {
 *     impl(libs, "coil.compose")
 * }
 * ```
 *
 * @param libs The [VersionCatalog] containing the dependency definitions
 * @param libraryName The alias of the library as defined in `libs.versions.toml`
 */
fun DependencyHandler.impl(libs: VersionCatalog, libraryName: String) {
    add("implementation", libs.findLibrary(libraryName).get().get())
}

/**
 * Adds a direct `implementation` dependency using a full Maven coordinate string.
 *
 * This is helpful when the dependency is not managed via the version catalog
 * and needs to be added directly by its group:name:version string.
 *
 * Example usage:
 * ```
 * impl("androidx.compose.foundation:foundation:1.6.0")
 * ```
 *
 * @param dep The full dependency coordinate as a string
 */
fun DependencyHandler.impl(dep: String) {
    add("implementation", create(dep))
}

/**
 * Adds a module dependency using the Gradle `project(...)` reference.
 *
 * This is useful for referencing sibling modules within a multi-module project
 * using a relative path like `:feature:auth` or `:presentation:core:utils`.
 *
 * Example usage:
 * ```
 * moduleImplementation(target, ":presentation:core:components")
 * ```
 *
 * @param target The current [Project] context (used to resolve the dependency)
 * @param modulePath The Gradle module path to include (e.g., ":domain:auth")
 */
fun DependencyHandlerScope.moduleImplementation(target: Project, modulePath: String) {
    val dependency = target.dependencies.project(mapOf("path" to modulePath))
    add("implementation", dependency)
}