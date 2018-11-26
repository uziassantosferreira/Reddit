
import org.jetbrains.kotlin.cli.jvm.main
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        applicationId = "com.uziassantosferreira.reddit"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = "1.0"
        testInstrumentationRunner = "com.uziassantosferreira.reddit.runner.ApplicationTestRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")
}

dependencies {
    implementation(Depends.Kotlin.stdlib)

    implementation(Depends.Support.appcompat)
    implementation(Depends.Support.material)
    implementation(Depends.Support.constraintLayout)
    implementation(Depends.Support.recyclerview)
    implementation(Depends.Support.cardview)

    implementation(Depends.Navigation.fragment)
    implementation(Depends.Navigation.ui)

    api(project(":presentation"))

    implementation(Depends.Koin.android)
    implementation(Depends.Koin.scope)
    implementation(Depends.Koin.viewmodel)

    implementation(Depends.Glide.glide)
    kapt(Depends.Glide.glidecompiler)

    androidTestImplementation(Depends.Test.extjunit)
    androidTestImplementation(Depends.Test.archCore)
    androidTestImplementation(Depends.Test.kakao)
    androidTestImplementation(Depends.Test.espresso)
    androidTestImplementation(Depends.Test.koin)
    androidTestImplementation(Depends.Test.runner)
    androidTestImplementation(Depends.Test.rules)
}
