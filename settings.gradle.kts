pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)

        id("org.jetbrains.kotlin.native") version "1.3.41"
    }
}

rootProject.name = "AdbTools"
