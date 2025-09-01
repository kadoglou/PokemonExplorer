import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * The base package path used for module and application-level namespace declarations.
 * Typically follows the reverse-domain convention.
 */
const val projectPath = "dev.kadoglou"

/**
 * The root name of the project or application.
 * This is used for identification, display, and build configuration.
 */
const val projectName = "pokemonexplorer"

/**
 * The target JVM bytecode version for Kotlin compilation.
 * JvmTarget.JVM_11 corresponds to Java 11 compatibility.
 */
val jvmTargetVersion = JvmTarget.JVM_11

/**
 * The Java version used for both source and target compatibility.
 * Ensures that Java compilation aligns with JVM target.
 */
val javaVersion = JavaVersion.VERSION_11

const val android_compileSdk = 36

const val android_minSdk = 26

const val android_targetSdk = 36
