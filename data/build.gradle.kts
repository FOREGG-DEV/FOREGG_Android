plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.foregg.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":domain"))
    implementation(AndroidX.CORE)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    implementation(AndroidX.DATA_STORE_PREFERENCES)

    //힐트
    implementation(Google.HILT_ANDROID)
    implementation(Google.HILT_CORE)
    kapt(Google.HILT_COMPILER)

    //RETROFIT
    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)
}