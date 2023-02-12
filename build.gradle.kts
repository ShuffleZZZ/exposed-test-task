plugins {
    kotlin("jvm") version "1.8.0"
}

group = "com.github.shufflezzz"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    kotlin {
        jvmToolchain(11)
    }
}
