dependencies {
    api("ai.ancf.lmos:kotlin-wot:0.1.3-SNAPSHOT")
    api("ai.ancf.lmos:kotlin-wot-binding-http:0.1.3-SNAPSHOT")
    api("ai.ancf.lmos:kotlin-wot-binding-websocket:0.1.3-SNAPSHOT")
    api(project(":lmos-kotlin-sdk-base"))
    implementation("org.slf4j:slf4j-api:2.0.16")
}