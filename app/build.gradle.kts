plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.project_quiz_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.project_quiz_app"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Room components (Java-only)
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version") // Dùng annotationProcessor cho Java

    // Optional - RxJava support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // Optional - Guava support for Room
    implementation("androidx.room:room-guava:$room_version")

    // Optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")
    // depedency  Material Components
    implementation ("com.google.android.material:material:1.10.0")

}