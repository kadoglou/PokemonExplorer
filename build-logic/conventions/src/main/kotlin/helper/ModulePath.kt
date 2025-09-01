package helper

import org.gradle.api.Project

/**
 * Converts the module's Gradle path to a dot-separated package-style path.
 *
 * Example:
 * ```
 * Gradle path: ":domain:game"
 * Result: "domain.game"
 * ```
 *
 * Useful for generating package names or namespaces from the module structure.
 */
val Project.modulePackagePath: String
    get() = path
        .split(":")
        .filter { it.isNotBlank() }
        .joinToString(".") { it.lowercase() }

/**
 * Returns the Gradle path of the module's parent hierarchy (excluding the current module).
 *
 * Example:
 * ```
 * Gradle path: ":domain:game"
 * Result: ":domain"
 * ```
 *
 * This is helpful when you want to reference sibling modules or derive relative module paths.
 */
val Project.modulePathWithoutName: String
    get() = path
        .split(":")
        .filter { it.isNotBlank() }
        .dropLast(1) // remove last (the module name)
        .joinToString(separator = ":", prefix = ":")