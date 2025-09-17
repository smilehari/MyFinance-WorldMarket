// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Defines the plugin for building Android applications.
// The `apply false` means it's available for modules to use but isn't applied to this root project.
    id("com.android.application") version "8.12.1" apply false
    id("org.jetbrains.kotlin.android") version "2.2.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20" apply false
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
    id("com.google.devtools.ksp") version "2.1.20-2.0.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20" apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
}