plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.foregg.presentation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.foregg.presentation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(AndroidX.CORE)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    implementation(AndroidX.FRAGMENT_KTX)
    implementation(AndroidX.NAVIGATION_UI_KTX)
    implementation(AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(AndroidX.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.SPLASH)
    implementation(AndroidX.THREE_TEN)
    implementation(AndroidX.SWIPE_REFRESH)
    implementation(AndroidX.DATA_STORE_PREFERENCES)

    //코루틴
    implementation(Kotlin.COROUTINES_CORE)
    implementation(Kotlin.COROUTINES)

    //힐트
    implementation(Google.HILT_ANDROID)
    implementation(Google.HILT_CORE)
    implementation(Google.FCM)
    implementation(Google.FCM_KTX)
    implementation("com.google.firebase:firebase-analytics:21.2.0")


    kapt(Google.HILT_COMPILER)

    implementation(KAKAO.AUTH)
    implementation(Google.GLIDE)

    implementation(Libraries.VIEWPAGER_INDICATOR)

    //Lottie
    implementation(Libraries.LOTTIE)
}