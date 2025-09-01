plugins {
    alias(libs.plugins.convention.target.android)
    alias(libs.plugins.convention.serialization)
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.logging)

    // Result
    implementation(projects.domain.result)
}
