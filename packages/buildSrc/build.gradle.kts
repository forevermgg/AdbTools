// Add support for precompiled script plugins: https://docs.gradle.org/current/userguide/custom_plugins.html#sec:precompiled_plugins
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

gradlePlugin {
    plugins {

    }
}

java {
    sourceCompatibility = Versions.sourceCompatibilityVersion
    targetCompatibility = Versions.targetCompatibilityVersion
}

repositories {
    google()
    gradlePluginPortal()
}


// Setup dependencies for building the buildScript.
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

// Setup dependencies for the buildscripts consuming the precompiled plugins
// These seem to propagate to all projects including the buildSrc/ directory, which also means
// they are not allowed to set the version. It can only be set from here.
dependencies {
    implementation("io.github.gradle-nexus:publish-plugin:${Versions.nexusPublishPlugin}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektPlugin}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    implementation("com.android.tools:r8:${Versions.Android.r8}")
    implementation("com.android.tools.build:gradle:${Versions.Android.buildTools}") // TODO LATER Don't know why this has to be here. See if we can remove this
    implementation("com.android.tools.build:gradle-api:${Versions.Android.buildTools}")
    implementation(kotlin("script-runtime"))
}
