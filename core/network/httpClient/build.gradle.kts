plugins {
    alias(libs.plugins.convention.target.android)
    alias(libs.plugins.convention.serialization)
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.koin.core)
    implementation(libs.bundles.logging)
}
