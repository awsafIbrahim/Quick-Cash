import java.util.Properties
import java.io.FileInputStream
plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}
val credentialsPropertiesFile: File = rootProject.file("local.properties")
val credentialsProperties = Properties()
credentialsProperties.load(FileInputStream(credentialsPropertiesFile))

android {
    namespace = "com.example.quickcash"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quickcash"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildFeatures {
            buildConfig = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "PAYPAL_CLIENT_ID", "\"${credentialsProperties["PAYPAL_CLIENT_ID"]}\"")

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
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:17.0.0")

    implementation ("com.paypal.sdk:paypal-android-sdk:2.15.3")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")
    testImplementation("org.mockito:mockito-core:5.6.0")
    testImplementation("org.testng:testng:6.9.6")
    androidTestImplementation ("org.mockito:mockito-android:3.11.2")
    androidTestUtil ("androidx.test:orchestrator:1.4.1")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

}