plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    implementation(projects.domain.repository.type)
    implementation(projects.domain.repository.pokemon)

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines)

    // Result
    implementation(projects.domain.result)

    // Test
    testImplementation(libs.bundles.test)
}