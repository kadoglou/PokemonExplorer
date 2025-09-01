plugins {
    alias(libs.plugins.convention.target.android)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.feature)
    implementation(projects.domain.repository.type)
}