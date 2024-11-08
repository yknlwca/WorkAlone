import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}
val nativeAppKey = localProperties.getProperty("KAKAO_NATIVE_APP_KEY") ?: ""
val oauthHost = localProperties.getProperty("KAKAO_OAUTH_HOST") ?: ""

android {
    buildFeatures {
        buildConfig = true
        viewBinding = false
    }
    namespace = "com.ssafy.workalone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.workalone"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        setProperty("archivesBaseName", "vision-quickstart")
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", nativeAppKey)
        resValue("string", "kakao_oauth_host", oauthHost)
    }


    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro", "proguard.cfg"
            )
        }
        debug {
            isDefault = true
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
        testBuildType = "debug"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
    val nav_version = "2.7.5"
    val compose_version = "1.6.0-alpha08"
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")


    implementation("com.kakao.sdk:v2-user:2.20.6") // 카카오 로그인 API 모듈

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Gson
    implementation(libs.gson)
    // 달력 compose
    implementation("io.github.boguszpawlowski.composecalendar:composecalendar:1.3.0")
    implementation("io.github.boguszpawlowski.composecalendar:kotlinx-datetime:1.3.0")

    //Lottie(콘페티 애니메이션 재생)
    implementation("com.airbnb.android:lottie-compose:5.2.0")
    implementation(libs.androidx.tools.core)
    implementation(libs.androidx.camera.core)

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")

    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //mlkit dependencies
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
    implementation("androidx.multidex:multidex:2.0.1")

    // Barcode model
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.1'

    // Object detection feature with bundled default classifier
    implementation("com.google.mlkit:object-detection:17.0.2")

    // Object detection feature with custom classifier support
    implementation("com.google.mlkit:object-detection-custom:17.0.2")

    // Face features
    implementation("com.google.mlkit:face-detection:16.1.7")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-face-detection:17.1.0'

    // Text features
    implementation("com.google.mlkit:text-recognition:16.0.1")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-text-recognition:19.0.1'
    implementation("com.google.mlkit:text-recognition-chinese:16.0.1")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-text-recognition-chinese:16.0.1'
    implementation("com.google.mlkit:text-recognition-devanagari:16.0.1")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-text-recognition-devanagari:16.0.1'
    implementation("com.google.mlkit:text-recognition-japanese:16.0.1")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-text-recognition-japanese:16.0.1'
    implementation("com.google.mlkit:text-recognition-korean:16.0.1")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-text-recognition-korean:16.0.1'

    // Image labeling
    implementation("com.google.mlkit:image-labeling:17.0.9")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-image-labeling:16.0.8'

    // Image labeling custom
    implementation("com.google.mlkit:image-labeling-custom:17.0.3")
    // Or comment the dependency above and uncomment the dependency below to
    // use unbundled model that depends on Google Play Services
    // implementation 'com.google.android.gms:play-services-mlkit-image-labeling-custom:16.0.0-beta5'

    // Pose detection with default models
    implementation("com.google.mlkit:pose-detection:18.0.0-beta5")
    // Pose detection with accurate models
    implementation("com.google.mlkit:pose-detection-accurate:18.0.0-beta5")

    // Selfie segmentation
    implementation("com.google.mlkit:segmentation-selfie:16.0.0-beta6")

    implementation("com.google.mlkit:camera:16.0.0-beta3")

    // Face Mesh Detection
    implementation("com.google.mlkit:face-mesh-detection:16.0.0-beta3")

    // Subject Segmentation
    implementation("com.google.android.gms:play-services-mlkit-subject-segmentation:16.0.0-beta1")

    // -------------------------------------------------------

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.google.guava:guava:27.1-android")

    // For how to setup gradle dependencies in Android X, see:
    // https://developer.android.com/training/testing/set-up-project#gradle-dependencies
    // Core library
    androidTestImplementation("androidx.test:core:1.4.0")

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")

    // Assertions
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    // ViewModel and LiveData
//    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
//
//    implementation ("androidx.appcompat:appcompat:1.2.0")
//    implementation ("androidx.annotation:annotation:1.2.0")
//    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")

    // CameraX
    implementation("androidx.camera:camera-camera2:1.4.0")
    implementation("androidx.camera:camera-lifecycle:1.0.0-SNAPSHOT")
    implementation("androidx.camera:camera-view:1.4.0")

    // On Device Machine Learnings
    implementation("com.google.android.odml:image:1.0.0-beta1")

    //compooseView dependencies
    // Compose dependencies
    implementation("androidx.activity:activity-compose:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")

}
configurations.all {
    exclude(group = "com.google.guava", module = "listenablefuture")
}
