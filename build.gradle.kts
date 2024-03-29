import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.diffplug.gradle.spotless.SpotlessExtension

val projectVersion: String by project
val projectGroup: String by project

plugins {
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.craigmiller160.gradle.defaults") version "1.1.0"
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("com.diffplug.spotless") version "6.6.1"
    `maven-publish`
}

group = projectGroup
version = projectVersion
java {
    sourceCompatibility = JavaVersion.VERSION_19
}

dependencies {
    val testcontainersVersion: String by project
//    testImplementation("org.jetbrains.kotlin:kotlin-noarg")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.kotest.extensions:kotest-assertions-arrow-jvm:1.3.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("com.h2database:h2")
    testImplementation("io.r2dbc:r2dbc-h2")
    testImplementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    val kotlinCoroutinesReactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor"
    compileOnly(kotlinCoroutinesReactor)
    testImplementation(kotlinCoroutinesReactor)

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
            jvmTarget = "19"
        }
    }

    test {
        useJUnitPlatform()
    }

    bootJar {
        enabled = false
    }

    jar {
        archiveClassifier.set("")
    }
}

configure<SpotlessExtension> {
    kotlin {
        ktfmt()
    }
}
