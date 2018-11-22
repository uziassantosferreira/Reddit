// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()

    }
    dependencies {

        classpath(Depends.BuildPlugins.android)
        classpath(Depends.BuildPlugins.kotlin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()

    }
}

task("clean") {
    delete(rootProject.buildDir)
}