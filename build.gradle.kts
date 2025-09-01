// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}

// Collect only subprojects that have Dokka V2 tasks
val dokkaModules = subprojects.filter { p ->
    p.tasks.findByName("dokkaGeneratePublicationHtml") != null ||
            p.tasks.findByName("dokkaGenerate") != null
}

// Build docs only for those modules
val generateAllModuleDocs = tasks.register("generateAllModuleDocs") {
    dependsOn(
        dokkaModules.mapNotNull { p ->
            p.tasks.findByName("dokkaGeneratePublicationHtml")
                ?: p.tasks.findByName("dokkaGenerate")
        }
    )
}