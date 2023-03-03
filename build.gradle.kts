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
    `maven-publish`
}

group = "io.github.craigmiller160"
version = "2.0.0-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_18
}

tasks.getByName<Jar>("jar") {
    enabled = true
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()

            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            val repo = if (project.version.toString().endsWith("-SNAPSHOT")) "maven-snapshots" else "maven-releases"
            url = uri("https://nexus-craigmiller160.ddns.net/repository/$repo")
            credentials {
                username = System.getenv("NEXUS_USER")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://nexus-craigmiller160.ddns.net/repository/maven-public")
    }
}

dependencies {
    val arrowVersion = "1.0.1"
    val kotestArrowVersion = "1.2.5"
    val postgresVersion = "42.4.0"
    val testcontainersVersion = "1.17.2"
    val kotlinResultVersion = "1.1.16"

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("io.kotest.extensions:kotest-assertions-arrow-jvm:$kotestArrowVersion")
    testImplementation("org.postgresql:postgresql:$postgresVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("com.h2database:h2")


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

    val kotlinResult = "com.michael-bull.kotlin-result:kotlin-result-jvm:$kotlinResultVersion"
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
