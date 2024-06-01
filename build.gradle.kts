val logback_version: String by project
val mockk_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.patchwork.ingo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}