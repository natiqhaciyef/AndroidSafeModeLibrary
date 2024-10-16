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

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.natiqhaciyef.androidsafemodelibrary.util.HiltTestRunner"
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

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    // Coil
    implementation("io.coil-kt:coil-compose:1.4.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    // Work manager
    val workManagerVersion = "2.8.1"
    implementation("androidx.work:work-runtime-ktx:$workManagerVersion")

    // TestImplementations
    implementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("org.mockito:mockito-core:4.7.0")

    // Android Test Implementations
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.mockito:mockito-android:4.7.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.mockito:mockito-core:4.7.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.43.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")

    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
    debugImplementation("androidx.fragment:fragment-testing:1.3.0-alpha08")
    kaptTest("com.google.dagger:hilt-android-compiler:2.44")
//    implementation("androidx.emoji2:emoji2-views-helper:1.4.0")

    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1") {
        exclude(group = "org.checkerframework", module = "checker")
    }

    implementation("com.google.protobuf:protobuf-kotlin:3.21.7") {
        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }

}

kapt {
    correctErrorTypes = true
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