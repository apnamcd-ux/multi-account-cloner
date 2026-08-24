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
include(":Bcore:black-fake")
include(":Bcore:black-hook")
include(":Bcore:pine-xposed")
include(":Bcore:pine-core")
include(":Bcore:pine-xposed-res")
include(":android-mirror")
