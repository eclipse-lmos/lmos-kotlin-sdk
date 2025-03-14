pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "lmos-kotlin-sdk"
include("lmos-kotlin-sdk-base")
include("lmos-kotlin-sdk-client")
include("lmos-kotlin-sdk-server")