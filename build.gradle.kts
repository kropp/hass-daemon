import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.61"
}

group = "name.kropp.hass"
version = "0.1"

val ktor_version = "1.2.4"

repositories {
  mavenCentral()
}

tasks.withType<KotlinCompile>().all {
  kotlinOptions.jvmTarget = "11"
  kotlinOptions.jvmTarget = "11"
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("io.ktor:ktor-client-websockets-jvm:$ktor_version")
  implementation("io.ktor:ktor-client-cio:$ktor_version")
}