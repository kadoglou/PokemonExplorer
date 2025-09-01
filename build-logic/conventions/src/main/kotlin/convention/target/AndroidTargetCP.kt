package convention.target

import ConventionPlugin
import android_compileSdk
import android_minSdk
import android_targetSdk
import com.android.build.gradle.LibraryExtension
import helper.Libs
import helper.modulePackagePath
import javaVersion
import jvmTargetVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension
import projectPath

/**
 * Configures Android Library modules.
 * Sets up namespace, compile SDK, min/target SDK, JVM target, and ProGuard.
 */
@Suppress("unused")
class AndroidTargetCP : ConventionPlugin() {

    override fun plugins() = listOf(
        Libs.Plugin.AndroidLibrary,
        Libs.Plugin.Kotlin.Android,
    )

    override fun KotlinAndroidExtension.kotlinBlock(target: Project): Unit = with(target) {
        compilerOptions {
            jvmTarget.set(jvmTargetVersion)
        }
    }

    override fun LibraryExtension.androidBlock(target: Project): Unit = with(target) {
        namespace = "$projectPath.$modulePackagePath"
        compileSdk = android_compileSdk

        defaultConfig {
            minSdk = android_minSdk
            testOptions.targetSdk = android_targetSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}