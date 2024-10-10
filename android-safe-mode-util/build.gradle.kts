plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.maven.publish.get().pluginId)
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.natiqhaciyef.android_safe_mode_util"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.appcompat)
    implementation(libs.material.v1110)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)

    //Dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Animation Library & Swipe Refresh Layout & ViewPager
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.lottie)
    implementation(libs.androidx.viewpager2.v100)

    // Work manager
    implementation(libs.androidx.work.runtime.ktx.v281)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.runtime.ktx)

    //Reflection
    implementation(kotlin("reflect"))

    //Data store
    implementation(libs.androidx.datastore.preferences.v100)
    implementation(libs.androidx.security.crypto.v100)

    //Gson
    implementation(libs.converter.gson)

    //Fragment ktx
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //View Model Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.navigation.runtime.ktx)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.natiqhaciyef"
                artifactId = "android-safe-mode-util"
                version = "1.0.0"
            }
        }
    }
}