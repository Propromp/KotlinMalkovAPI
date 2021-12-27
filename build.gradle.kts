import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "net.propromp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
tasks.create("writeVersionToFile") {
        file("build/version.txt").writeText(version as String)
}