plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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
}


dependencies {
    api(Depends.Rx.android)

    api(Depends.Koin.android)

    implementation(Depends.Retrofit.retrofit)
    implementation(Depends.Retrofit.adapter)
    implementation(Depends.Retrofit.gson)
    implementation(Depends.Retrofit.logginginterceptor)

    api(project(":domain"))

    testImplementation(Depends.Test.kluent)
    testImplementation(Depends.Test.mockito)
    testImplementation(Depends.Test.junit)
    testImplementation(Depends.Test.mockwebserver)
    testImplementation(Depends.Test.koin)

}
