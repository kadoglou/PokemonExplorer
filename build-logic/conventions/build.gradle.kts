plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
    implementation(libs.dokka.gradle.plugin)
}

gradlePlugin {

    plugins {

        register("target.application.entry") {
            id = libs.plugins.convention.target.application.entry.get().pluginId
            implementationClass = "convention.target.ApplicationEntryCP"
        }

        register("target.android") {
            id = libs.plugins.convention.target.android.get().pluginId
            implementationClass = "convention.target.AndroidTargetCP"
        }

        register("serialization") {
            id = libs.plugins.convention.serialization.get().pluginId
            implementationClass = "convention.plugin.SerializationCP"
        }

    }
}
