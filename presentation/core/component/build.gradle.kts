plugins {
    alias(libs.plugins.convention.target.android)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.feature)
    implementation(libs.bundles.coil)

    implementation(projects.domain.repository.type)
    implementation(libs.lottie)
    implementation(projects.presentation.resources)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}