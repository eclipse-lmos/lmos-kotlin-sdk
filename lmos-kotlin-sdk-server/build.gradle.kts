plugins {
    kotlin("plugin.spring") version "1.9.10"
    id("org.springframework.boot") version "3.1.5" // Use the latest compatible version
    id("io.spring.dependency-management") version "1.1.3"
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.eclipse.thingweb:kotlin-wot-spring-boot-starter:0.1.0-SNAPSHOT")
    api("org.eclipse.thingweb:kotlin-wot-reflection:0.1.0-SNAPSHOT")
    api(project(":lmos-kotlin-sdk-base"))

    testImplementation(platform("io.ktor:ktor-bom:3.1.0"))
    testImplementation("org.eclipse.lmos:arc-spring-boot-starter:0.122.0-M2")
    testImplementation("org.eclipse.lmos:arc-azure-client:0.131.0-SNAPSHOT")

    testImplementation(project(":lmos-kotlin-sdk-client"))
    testImplementation("org.eclipse.thingweb:kotlin-wot-binding-http:0.1.0-SNAPSHOT")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}