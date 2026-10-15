plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ucchash.billing"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ucchash.billing"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }
}
