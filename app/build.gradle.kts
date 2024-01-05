plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.zakiahmedjava"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zakiahmedjava"
        minSdk = 24
        targetSdk = 33
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("org.chromium.net:cronet-embedded:113.5672.61")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Animated Icon
    implementation("com.airbnb.android:lottie:3.4.0")

    // GSON Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Retrofit API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Glide Dependency
    implementation("com.github.bumptech.glide:glide:4.12.0")

    implementation ("com.google.firebase:firebase-bom:31.0.2")
    implementation ("com.google.android.gms:play-services-auth:20.3.0")

    implementation ("com.facebook.android:facebook-android-sdk:latest.release")

    implementation ("com.squareup.picasso:picasso:2.8")
}