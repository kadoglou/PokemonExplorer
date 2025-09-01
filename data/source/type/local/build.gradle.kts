plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    // Room
    implementation(libs.room.runtime)

    // KotlinX
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines)

    // Koin
    implementation(libs.koin.core)

    // Result
    implementation(projects.domain.result)
}