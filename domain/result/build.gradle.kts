plugins {
    alias(libs.plugins.convention.target.android)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.ktor.client.core)
}