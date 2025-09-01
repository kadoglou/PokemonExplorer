plugins {
    alias(libs.plugins.convention.target.android)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    // Core Feature
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.feature)
    implementation(projects.presentation.core.component)
    implementation(libs.compose.navigation)

    // Koin
    implementation(libs.bundles.koin)

    // Domain
    implementation(projects.domain.repository.pokemon)
    implementation(projects.domain.repository.type)
    implementation(projects.domain.usecase.type)
    implementation(projects.domain.usecase.pokemon)

    implementation(projects.core.paginator)
    implementation(projects.core.settings)

    implementation(projects.presentation.resources)


    implementation(libs.lottie)

    // Result
    implementation(projects.domain.result)
}
