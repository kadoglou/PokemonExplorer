plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(projects.domain.result)
}