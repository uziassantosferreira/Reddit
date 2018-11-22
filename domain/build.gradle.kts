plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(Depends.Kotlin.stdlib)
    implementation(Depends.Rx.kotlin)

    testImplementation(Depends.Test.kluent)
    testImplementation(Depends.Test.mockito)
}
