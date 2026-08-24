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

// Directly include BlackBox modules from the cloned Bcore folder
include(":Bcore")
project(":Bcore").projectDir = file("Bcore/Bcore")

include(":Bcore:black-fake")
project(":Bcore:black-fake").projectDir = file("Bcore/Bcore/black-fake")

include(":Bcore:black-hook")
project(":Bcore:black-hook").projectDir = file("Bcore/Bcore/black-hook")

include(":Bcore:pine-core")
project(":Bcore:pine-core").projectDir = file("Bcore/Bcore/pine-core")

include(":Bcore:pine-xposed")
project(":Bcore:pine-xposed").projectDir = file("Bcore/Bcore/pine-xposed")

include(":Bcore:pine-xposed-res")
project(":Bcore:pine-xposed-res").projectDir = file("Bcore/Bcore/pine-xposed-res")
