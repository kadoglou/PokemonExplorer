plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.kotlin)
    implementation(projects.data.db)

    // Sources
    implementation(projects.data.source.type.local)
    implementation(projects.data.source.type.remote)

    // Domain
    implementation(projects.domain.repository.type)

    // Koin
    implementation(libs.koin.core)

    // Result
    implementation(projects.domain.result)
}