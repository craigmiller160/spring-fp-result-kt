import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    val kotlinVersion = "1.6.21"

    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("com.diffplug.spotless") version "6.6.1"
}

group = "io.craigmiller160"
version = "0.0.1-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_18
}

repositories {
    mavenCentral()
}

dependencies {
    val arrowVersion = "1.0.1"
    val kotestArrowVersion = "1.2.5"

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow-jvm:$kotestArrowVersion")

    val springDataJpa = "org.springframework.boot:spring-boot-starter-data-jpa"
    compileOnly(springDataJpa)
    testImplementation(springDataJpa)

    val springWeb = "org.springframework.boot:spring-boot-starter-web"
    compileOnly(springWeb)
    testImplementation(springWeb)

    val arrowKt = "io.arrow-kt:arrow-core-jvm:$arrowVersion"
    compileOnly(arrowKt)
    testImplementation(arrowKt)

    val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
    compileOnly(jacksonModuleKotlin)
    testImplementation(jacksonModuleKotlin)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "18"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

configure<SpotlessExtension> {
    kotlin {
        ktfmt()
    }
}