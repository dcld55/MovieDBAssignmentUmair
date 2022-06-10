package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.koinKotlin(koin_version : String) {
    // Koin Core features
    implementation("io.insert-koin:koin-core:$koin_version")
    // Koin Test features
    testImplementation("io.insert-koin:koin-test:$koin_version")

    // Koin main features for Android
    implementation("io.insert-koin:koin-android:$koin_version")
    // Java Compatibility
    implementation("io.insert-koin:koin-android-compat:$koin_version")
    // Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:$koin_version")
    // Navigation Graph
    implementation("io.insert-koin:koin-androidx-navigation:$koin_version")
    // Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:$koin_version")
}