plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Activate Kotlin Symbol Processing (KSP) for Room Annotations
    id("com.google.devtools.ksp")
}

android {
    namespace = "edu.ufp.pam.project.healthquiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.ufp.pam.project.healthquiz"
        minSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // Local binary dependency
    // Gradle declares dependencies on JAR files inside the project's module_name/libs/ directory
    // Because gradle reads paths relative to the build.gradle.kts
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Target the latest platform features and APIs while also supporting older devices.
    implementation("androidx.core:core-ktx:1.12.0")
    // Allows access to new APIs on older API versions of the platform (many using Material Design).
    implementation("androidx.appcompat:appcompat:1.6.1")
    // Static library in order to use APIs that provide implementations of the Material Design specification.
    implementation("com.google.android.material:material:1.11.0")
    // Position and size widgets in a flexible way with relative positioning.
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Display large sets of data in your UI while minimizing memory usage.
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // Navigation is a framework for navigating between 'destinations' within an Android application
    // that provides a consistent API whether destinations are implemented as Fragments, Activities,
    // or other components.
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    // Aligns all the Kotlin libraries with the same version and avoids duplicates.
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.22"))

    // Deprecated artifact - Since Android 8, background check restrictions make this class no longer useful
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Support for ViewModel and LiveData
    // KTX - Kotlin Extensions of Jetpack that simplify working with other APIs
    // Lifecycle-aware components perform actions in response to a change in the lifecycle status of
    // another component, such as activities and fragments. These components help you produce
    // better-organized, and often lighter-weight code, that is easier to maintain.
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    //Manage activity state
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    // Some UI AndroidX libraries use this lightweight import for Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // For Kotlin use lifecycle-reactivestreams-ktx
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycleVersion")

    // Support for Room DB management
    // The Room persistence library provides an abstraction layer over SQLite to allow for more
    // robust database access while harnessing the full power of SQLite.
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    // To use Kotlin Symbolic Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$roomVersion")
    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$roomVersion")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$roomVersion")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")
    // optional - Room test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}