plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    // Result
    implementation(projects.domain.result)
}