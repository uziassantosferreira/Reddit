plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    api(Depends.Rx.android)
    api(Depends.Lifecycle.extensions)
    api(Depends.Paging.paging)

    kapt(Depends.Lifecycle.compiler)

    api(project(":domain"))
    api(project(":data"))

    testImplementation(Depends.Test.kluent)
    testImplementation(Depends.Test.mockito)
    testImplementation(Depends.Test.archCore)
    testImplementation(Depends.Test.junit)
}