import helper.impl

plugins {
    alias(libs.plugins.convention.target.application.entry)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.serialization)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.compose.navigation)

    // Logging
    implementation(libs.bundles.logging)

    // Coil
    implementation(libs.bundles.coil)

    // Koin
    implementation(libs.bundles.koin)

    implementation(libs.lottie)

    // Project Modules
    implementation(projects.core.network.httpClient)
    implementation(projects.core.system.connectivity)

    implementation(projects.data.db)

    implementation(projects.data.source.pokemon.local)
    implementation(projects.data.source.pokemon.remote)
    implementation(projects.data.source.type.local)
    implementation(projects.data.source.type.remote)

    implementation(projects.data.repository.pokemon)
    implementation(projects.data.repository.type)

    implementation(projects.domain.repository.type)
    implementation(projects.domain.usecase.pokemon)
    implementation(projects.domain.usecase.type)

    implementation(projects.presentation.resources)

    // Feature
    implementation(projects.presentation.feature.searchList)


    // Result
    implementation(projects.domain.result)
}