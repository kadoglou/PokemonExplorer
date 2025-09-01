plugins {
    id("org.jetbrains.dokka")       // apply Dokka to the target module
}

dependencies {
    add("dokkaPlugin", "org.jetbrains.dokka:android-documentation-plugin:2.0.0")
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokkaDir"))
    }

}