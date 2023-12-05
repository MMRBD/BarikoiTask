plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
//    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.mmrbd.barikoitask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mmrbd.barikoitask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "BARIKOI_BASE_URL", project.properties["BARIKOI_BASE_URL"].toString())
        buildConfigField("String", "BARIKOI_API_KEY", project.properties["BARIKOI_API_KEY"].toString())
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}
kapt {
    correctErrorTypes = true
}



dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("org.maplibre.gl:android-sdk:10.2.0")
    implementation("org.maplibre.gl:android-plugin-annotation-v9:1.0.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")

    implementation("io.insert-koin:koin-core:3.5.0")
    implementation ("io.insert-koin:koin-android:3.5.0")
}