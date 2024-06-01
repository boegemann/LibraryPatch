val logback_version: String by project
val mockk_version: String by project
val junit_jupiter_version: String by project

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
    testImplementation("io.mockk:mockk:${mockk_version}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junit_jupiter_version}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junit_jupiter_version}")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}