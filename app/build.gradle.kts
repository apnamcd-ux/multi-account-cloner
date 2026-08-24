plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.multiclone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.multiclone"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Maps to BlackBox's group and module identity so Gradle routes it to the local Bcore build
    implementation("top.niunaijun.blackbox:Bcore:1.0.0")
}
