plugins {
    // id("com.android.library") apply false
    kotlin("jvm")
    kotlin("kapt")
    `java-gradle-plugin`
    `java`
}

allprojects {
    buildscript {
        repositories {
            mavenCentral()
        }
    }
    repositories {
        mavenCentral()
    }

    version = "1.0.0"
    group = "com.mgg.base"

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}