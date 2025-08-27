plugins {
    kotlin("jvm") version "2.2.10"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
    id("org.cadixdev.licenser") version "0.6.1"
    id("com.vanniktech.maven.publish") version "0.34.0"
    id("org.cyclonedx.bom") version "2.2.0" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "com.vanniktech.maven.publish")
    apply(plugin = "org.cadixdev.licenser")
    apply(plugin = "org.cyclonedx.bom")

    group = "org.eclipse.lmos"
    version = "0.1.0-SNAPSHOT"

    license {
        header(rootProject.file("LICENSE"))
        include("**/*.java")
        include("**/*.kt")
        include("**/*.yaml")
        exclude("**/*.properties")
    }

    dependencies {
        testImplementation(kotlin("test"))
        testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test")
        testImplementation("io.mockk:mockk:1.13.13")
    }

    tasks.register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
        dependsOn("classes")
    }

    tasks.register<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        from(tasks.javadoc)
        dependsOn("javadoc")
    }

    artifacts {
        add("archives", tasks["sourcesJar"])
        add("archives", tasks["javadocJar"])
    }

    mavenPublishing {
        publishToMavenCentral(automaticRelease = true)
        signAllPublications()

        pom {
            name = "lmos-kotlin-sdk"
            description = "The LMOS Kotlin SDK to devevlop WoT-enabled Agents."
            url = "https://github.com/eclipse-lmos/lmos-kotlin-sdk"
            licenses {
                license {
                    name = "Apache-2.0"
                    distribution = "repo"
                    url = "https://github.com/eclipse-lmos/lmos-kotlin-sdk/blob/master/LICENSES/Apache-2.0.txt"
                }
            }
            developers {
                developer {
                    id = "robwin"
                    name = "Robert Winkler"
                    email = "opensource@telekom.de"
                }
            }
            scm {
                url = "https://github.com/eclipse-thingweb/lmos-kotlin-sdk.git"
            }
        }
    }

    tasks.test {
        useJUnitPlatform()

        // Configure proxy only if the required properties are set
        val proxyHost = System.getProperty("http.proxyHost")
        val proxyPort = System.getProperty("http.proxyPort")

        if (!proxyHost.isNullOrEmpty() && !proxyPort.isNullOrEmpty()) {
            systemProperty("http.proxyHost", proxyHost)
            systemProperty("http.proxyPort", proxyPort)
        }
    }

    kotlin {
        jvmToolchain(21)
    }

}

allprojects {
    repositories {
        //mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}
