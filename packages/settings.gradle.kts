rootProject.name = "mgg-kotlin"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // Project setup - See './CONTRIBUTING.md' for description of the project structure and various options.
        getPropertyValue("testRepository")?.let {
            maven("file://${rootDir.absolutePath}/$it")
        }
    }
}
fun getPropertyValue(propertyName: String): String? {
    val systemValue: String? = System.getenv(propertyName)
    if (extra.has(propertyName)) {
        return extra[propertyName] as String?
    }
    return systemValue
}


(getPropertyValue("includeSdkModules")?.let { it.toBoolean() } ?: true).let {
    if (it) {
        include(":cinterop")
    }
}
