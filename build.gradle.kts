import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    `maven-publish`
}

group = "net.propromp"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
tasks.create("writeVersionToFile") {
    File("versions.txt").apply {
        if(!exists()) {
            createNewFile()
        }
        writeText(version as String)
    }
}

publishing {
    publications {
        create("maven_public",MavenPublication::class.java) {
            groupId = "net.propromp"
            artifactId = "kotlinmalkovapi"
            version = version
            from(components.getByName("java"))
        }
    }
}