# LMOS Kotlin SDK

The LMOS Kotlin SDK allows you to connect to a Web of Things-enabled agents by pointing to its well-known Thing Description (TD). You can consume events and interact with the agent through chat.

```kotlin
val agent = WotConversationalAgent
    .create("http://localhost:9080/weather-agent")

// Consuming an event from the agent
agent.consumeEvent("agentEvent") {
    println("Event: $it")
}

// Interacting with the agent via chat
val answer = agent.chat("What is the weather in London?")
```

## Adding the Library to Your Project

To use the `lmos-kotlin-sdk-client` in your project, include the following dependency in your build configuration.

### Gradle

Add the dependency to your `build.gradle.kts` file:
```kotlin
dependencies {
    implementation("org.eclipse.lmos:lmos-kotlin-sdk-client:0.1.0-SNAPSHOT")
}
```

Make sure to include the Sonatype Snapshots repository in your `repositories` block:
```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
```

### Maven
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>org.eclipse.lmos</groupId>
    <artifactId>lmos-kotlin-sdk-client</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Include the Sonatype Snapshots repository in your `<repositories>` section:
```xml
<repositories>
    <repository>
        <id>sonatype-snapshots</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
```
