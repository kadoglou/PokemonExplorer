plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.kotlin)
    implementation(projects.data.db)

    // Sources
    implementation(projects.data.source.pokemon.local)
    implementation(projects.data.source.pokemon.remote)

    // Domain
    implementation(projects.domain.repository.pokemon)
    implementation(projects.domain.repository.type)

    // Koin
    implementation(libs.koin.core)

    // Logging
    implementation(libs.bundles.logging)

    // Result
    implementation(projects.domain.result)

    // Settings
    implementation(projects.core.settings)

}