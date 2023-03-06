val projectName: String by settings

rootProject.name = projectName

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
    }

    // TODO this should go in init
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://nexus-craigmiller160.ddns.net/repository/maven-public")
        }
    }
}