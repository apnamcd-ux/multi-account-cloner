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

includeBuild("Bcore") {
    dependencySubstitution {
        // BlackBox defines the submodule as ':Bcore' inside its root project
        substitute(module("top.niunaijun.blackbox:Bcore")).using(project(":Bcore"))
    }
}
