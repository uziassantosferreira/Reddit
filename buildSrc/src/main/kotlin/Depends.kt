
object Depends {
    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    object Support {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
        const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    }

    object Lifecycle {
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val compiler = "androidx.lifecycle:lifecycle-compiler-ktx:${Versions.lifecycle}"
        const val reactivestreams = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
    }

    object BuildPlugins {
        const val android = "com.android.tools.build:gradle:${Versions.androidPlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

        object Koin {
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val scope = "org.koin:koin-android-scope:${Versions.koin}"
        const val viewmodel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val logginginterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.logging}"
    }

    object Navigation {
        const val fragment = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        const val ui = "android.arch.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    }

    object Rx {
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
        const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    }

    object Test {
        const val kluent = "org.amshove.kluent:kluent:${Versions.kluent}"
        const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito}"
        const val archCore = "android.arch.core:core-testing:${Versions.lifecycle}"
        const val kakao = "com.agoda.kakao:kakao:${Versions.kakao}"
        const val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
        const val junit = "junit:junit:${Versions.junit}"
        const val mockwebserver = "com.squareup.okhttp:mockwebserver:${Versions.mockwebserver}"
        const val koin = "org.koin:koin-test:${Versions.koin}"
    }
}