import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.diffplug.gradle.spotless.SpotlessExtension

val projectVersion: String by project
val projectGroup: String by project

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.craigmiller160.gradle.defaults") version "1.0.0-SNAPSHOT"
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("com.diffplug.spotless") version "6.6.1"
    `maven-publish`
}

group = projectGroup
version = projectVersion
java {
    sourceCompatibility = JavaVersion.VERSION_18
}

tasks.getByName<Jar>("jar") {
    enabled = true
    archiveClassifier.set("")
}

dependencies {
    val testcontainersVersion: String by project

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.kotest.extensions:kotest-assertions-arrow-jvm:1.2.5")
    testImplementation("org.postgresql:postgresql:42.4.0")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("com.h2database:h2")


    val springDataJpa = "org.springframework.boot:spring-boot-starter-data-jpa"
    compileOnly(springDataJpa)
    testImplementation(springDataJpa)

    val springWeb = "org.springframework.boot:spring-boot-starter-web"
    compileOnly(springWeb)
    testImplementation(springWeb)

    val arrowKt = "io.arrow-kt:arrow-core-jvm:1.0.1"
    compileOnly(arrowKt)
    testImplementation(arrowKt)

    val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
    compileOnly(jacksonModuleKotlin)
    testImplementation(jacksonModuleKotlin)

    val kotlinResult = "com.michael-bull.kotlin-result:kotlin-result-jvm:1.1.16"
    compileOnly(kotlinResult)
    testImplementation(kotlinResult)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "18"
        }
    }

    test {
        useJUnitPlatform()
    }

    bootJar {
        enabled = false
    }

    jar {
        enabled = true
        archiveClassifier.set("")
    }
}

configure<SpotlessExtension> {
    kotlin {
        ktfmt()
    }
}
