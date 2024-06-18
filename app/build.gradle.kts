plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ozimos.baseproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ozimos.baseproject"
        minSdk = 26
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
        viewBinding = true
    }


    buildFeatures.buildConfig = true
    val myDimention = "default"
    flavorDimensions.add(myDimention)
    productFlavors {
        create("dev") {
            dimension = myDimention
            buildConfigField("String", "TOKEN_GITHUB", "\"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\"")
        }
        create("prod") {
            dimension = myDimention
            buildConfigField("String", "TOKEN_GITHUB", "\"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\"")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // dependency injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // image loader
    implementation(libs.coil)

    // network
    implementation(libs.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
}

kapt {
    correctErrorTypes = true
}