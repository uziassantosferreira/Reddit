plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    api(Depends.Kotlin.stdlib)
    api(Depends.Rx.kotlin)

    testImplementation(Depends.Test.kluent)
    testImplementation(Depends.Test.mockito)
}
