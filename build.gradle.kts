import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.kyleescobar"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.17.1")
    implementation("com.github.ajalt:clikt:2.8.0")
}

application {
    mainClass.set("dev.kyleescobar.osrsgamepacks.MainKt")
}

tasks.shadowJar {
    archiveVersion.set("")
    archiveClassifier.set("")
    archivesName.set("gamepack-downloader")
}