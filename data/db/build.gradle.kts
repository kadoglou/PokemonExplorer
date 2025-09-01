plugins {
    alias(libs.plugins.convention.target.android)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

dependencies {
    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // KotlinX
    implementation(libs.kotlinx.datetime)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Daos
//    implementation(projects.data.source.pokemonDetails.local)
    implementation(projects.data.source.pokemon.local)
    implementation(projects.data.source.type.local)
}

room {
    schemaDirectory("$projectDir/schemas")
}
