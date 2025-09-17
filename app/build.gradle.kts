import org.gradle.kotlin.dsl.testImplementation

plugins {
    // Applies the Android application plugin, which makes this a runnable app module.
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    // Applies the Hilt plugin for dependency injection.
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id ("kotlinx-serialization")
    id("kotlin-parcelize")

}


ksp {
    arg("verbose", "true")
    arg("ksp.incremental", "false")
}

android {
    namespace = "com.harishri.financepal"
    compileSdk = 36

    defaultConfig {
        // The unique application ID for your app.
        applicationId = "com.harishri.financepal"
        // Sets the minimum SDK version your app will support.
        minSdk = 24
        targetSdk = 36
        // The version code for internal tracking.
        versionCode = 1
        // The user-facing version name.
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
// In the build.gradle.kts file for the `network` and `interceptor` modules

    android {
        // ... other android configurations

        buildFeatures {
            // Enable BuildConfig generation for this module
            buildConfig = true
        }
    }
    // Configuration for different build types (e.g., debug and release).
    buildTypes {
        // Defines the release build type.
        release {
            // Enables code shrinking.
            isMinifyEnabled = true
            // Block rooted devices in the release build
            buildConfigField("boolean", "BLOCK_ROOTED_DEVICES", "true")
            // Specifies the ProGuard rules file.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
        debug {
            // ...
            // Don't block rooted devices during debugging
            buildConfigField("boolean", "BLOCK_ROOTED_DEVICES", "false")
        }
    }
    // Configuration for Kotlin compilation.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        composeOptions {
                //kotlinCompilerExtensionVersion = "1.5.1"
        }
    }

    // Enables Jetpack Compose features.
    buildFeatures {
        compose = true
    }
        // Configures Jetpack Compose compiler.
        composeOptions {
            //kotlinCompilerExtensionVersion = "1.5.1"
        }
    kotlinOptions {
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    // Use the latest stable version
    val workVersion = "2.8.1"
    val roomVersion = "2.7.2"
    val hiltVersion = "1.2.0"
    val hiltAndroidVersion = "2.57.1"
    val navVersion = "2.9.3"
    val retrofitversion ="3.0.0"
    val okHttpversion = "5.1.0"
    val materialVersion = "1.3.2"

    // Kotlin
    // Base Android libraries.
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation ("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material:material-icons-core")

    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // OkHttp for logging network calls
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    // Room for offline database

    implementation("androidx.room:room-runtime:$roomVersion")
    //annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Room with Coroutines and Flow
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")


    // Hilt with ViewModel
    implementation("androidx.hilt:hilt-work:$hiltVersion")
    ksp("androidx.hilt:hilt-compiler:$hiltVersion")
    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltAndroidVersion")

    // Hilt with Jetpack Navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    implementation("androidx.work:work-runtime-ktx:$workVersion")

    // Image loading
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.4.0")
    implementation("com.google.android.gms:play-services-auth-base:18.3.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Firebase Authentication
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Push Notifications
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.0")

    //navigation related libraries

    implementation("androidx.navigation:navigation-compose:$navVersion")

    implementation("androidx.compose.material3:material3:$materialVersion")
    implementation("androidx.compose.material3:material3-window-size-class:$materialVersion")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.5.0-alpha03")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:$retrofitversion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitversion")

    implementation("com.squareup.okhttp3:okhttp:$okHttpversion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpversion")

    // Security
    //implementation("com.android.support:support-annotations:28.0.0")
    //try using androidx hari
    implementation("androidx.annotation:annotation:1.9.1")

    //Json serialization
    //added for json serialization and conversion
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // For basic icons like `Icons.Default.List`
    /*val materialIcons = "1.9.0"
    implementation("androidx.compose.material:material-icons-core:$materialIcons")

    // For extended icons like `Icons.Filled.AccountBalanceWallet` and `TrendingUp`
    implementation("androidx.compose.material:material-icons-extended:$materialIcons")*/

    // For local unit tests (on a JVM)
    testImplementation ("junit:junit:4.13.2")
    testImplementation("androidx.room:room-testing:${roomVersion}")

    // For Android-specific unit tests (running on a device or emulator)
    androidTestImplementation  ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation  ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}