import org.jetbrains.kotlin.konan.target.KonanTarget
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:${Versions.atomicfu}")
    }
}

apply(plugin = "kotlinx-atomicfu")
// AtomicFu cannot transform JVM code. Throws
// ClassCastException: org.objectweb.asm.tree.InsnList cannot be cast to java.lang.Iterable
project.extensions.configure(kotlinx.atomicfu.plugin.gradle.AtomicFUPluginExtension::class) {
    transformJvm = false
}

// Directory for generated Version.kt holding VERSION constant
val versionDirectory = "$buildDir/generated/source/version/"

// Types of builds supported
enum class BuildType(val type: String, val buildDirSuffix: String) {
    DEBUG( type ="Debug", buildDirSuffix = "-dbg"),
    RELEASE( type ="Release", buildDirSuffix = "");
}

// CONFIGURATION is an env variable set by XCode or could be passed to the gradle task to force a certain build type
//               * Example: to force build a release
//               realm-kotlin/packages> CONFIGURATION=Release ./gradlew capiIosArm64
//               * to force build a debug (default BTW) use
//               realm-kotlin/packages> CONFIGURATION=Debug ./gradlew capiIosArm64
//               default is 'Release'
val buildType: BuildType = if ((System.getenv("CONFIGURATION") ?: "RELEASE").equals("Release", ignoreCase = true)) {
    BuildType.RELEASE
} else {
    BuildType.DEBUG
}

kotlin {
    jvm()
    targets.all {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += listOf("-opt-in=kotlin.ExperimentalUnsignedTypes")
                freeCompilerArgs += listOf("-opt-in=kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}