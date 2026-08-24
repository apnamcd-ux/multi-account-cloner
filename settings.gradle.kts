pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "multi-account-cloner"

include(":app")
include(":Bcore")

// Map Bcore internal modules to BlackBox repository layout
include(":Bcore:Bcore")
project(":Bcore:Bcore").projectDir = file("Bcore/Bcore")

include(":Bcore:black-fake")
project(":Bcore:black-fake").projectDir = file("Bcore/black-fake")

include(":Bcore:pine:pine-xposed")
project(":Bcore:pine:pine-xposed").projectDir = file("Bcore/pine/pine-xposed")

include(":Bcore:pine:pine-core")
project(":Bcore:pine:pine-core").projectDir = file("Bcore/pine/pine-core")
