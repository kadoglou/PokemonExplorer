package convention.plugin

import ConventionPlugin
import helper.Libs
import helper.impl
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension

@Suppress("unused")
class SerializationCP : ConventionPlugin() {

    override fun plugins() = listOf(
        Libs.Plugin.Kotlin.Serialization
    )

    override fun KotlinAndroidExtension.kotlinBlock(target: Project): Unit = with(target) {
        dependencies {
            impl(libs, Libs.Library.KotlinX.Serialization)
        }
    }
}