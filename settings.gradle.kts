@file:Suppress("UnstableApiUsage")

// region Settings

rootProject.name = "PokemonExplorer"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// endregion

// region Modules

// Convention plugins
includeBuild("build-logic")

// MainApp
include(":app")

// Core
include(":core:network:httpClient")
include(":core:paginator")
include(":core:system:connectivity")
include(":core:settings")

// Data
include(":data:db")

// Data --> Source
include(":data:source:pokemon:local")
include(":data:source:pokemon:remote")
include(":data:source:type:local")
include(":data:source:type:remote")

// Data --> Repository
include(":data:repository:pokemon")
include(":data:repository:type")

// Domain
include(":domain:result")

// Domain  -> Repository
include(":domain:repository:pokemon")
include(":domain:repository:type")

// Domain -> Usecase
include(":domain:usecase:pokemon")
include(":domain:usecase:type")

// Presentation --> Resources
include(":presentation:resources")

// Presentation --> Core
include(":presentation:core:component")

// Presentation --> Feature
include(":presentation:feature:searchList")

// endregion
 