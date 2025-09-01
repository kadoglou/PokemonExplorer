package convention.target

import ConventionPlugin
import android_compileSdk
import android_minSdk
import android_targetSdk
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import helper.Libs
import javaVersion
import jvmTargetVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension
import projectName
import projectPath

/**
 * Configures Android Application Entry Point module.
 * Applies Android application settings, Kotlin JVM options, and Compose.
 */
@Suppress("unused")
class ApplicationEntryCP : ConventionPlugin() {

    override fun plugins() = listOf(
        Libs.Plugin.AndroidApplication,
        Libs.Plugin.Kotlin.Android,
    )

    override fun KotlinAndroidExtension.kotlinBlock(target: Project): Unit = with(target) {
        compilerOptions {
            jvmTarget.set(jvmTargetVersion)
        }
    }

    override fun configureProject(target: Project): Unit = with(target) {
        extensions.configure<BaseAppModuleExtension> {
            namespace = "$projectPath.$projectName"
            compileSdk = android_compileSdk

            defaultConfig {
                applicationId = "$projectPath.$projectName"
                minSdk = android_minSdk
                targetSdk = android_targetSdk
                versionCode = 1
                versionName = "1.0"
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }

            buildFeatures {
                compose = true
            }

            packaging {
                resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}