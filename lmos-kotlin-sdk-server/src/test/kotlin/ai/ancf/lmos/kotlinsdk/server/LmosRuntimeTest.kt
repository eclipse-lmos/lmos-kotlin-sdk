/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package ai.ancf.lmos.kotlinsdk.server



import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult
import org.eclipse.lmos.sdk.model.AnonymizationEntity
import org.eclipse.lmos.sdk.model.Message
import org.eclipse.lmos.sdk.server.LmosAgent
import org.eclipse.lmos.sdk.server.LmosRuntime
import org.eclipse.thingweb.Servient
import org.eclipse.thingweb.binding.http.HttpProtocolServer
import org.junit.jupiter.api.Test


class LmosRuntimeTest {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            }
        }
    }

    @Test
    fun test() = runBlocking {
        val lmosAgent = LmosAgent(
            id = "my-agent-id",
            title = "my-agent-title",
            description = "my agent description",
            capabilities = "capabilities",
            chat = { agentRequest -> someAgentResult(agentRequest) },
        )

        val servient = Servient(servers = listOf(HttpProtocolServer()))
        val lmosRuntime = LmosRuntime(servient)
        lmosRuntime.start()
        lmosRuntime.add(lmosAgent)

        val response: JsonNode = client.get("http://localhost:8080/${lmosAgent.id}").body()
        assertThat(response.isObject).isTrue()
        assertThat(response.get("id").asText()).isEqualTo(lmosAgent.id)

        lmosRuntime.stop()
    }

    private fun someAgentResult(agentRequest: AgentRequest) = AgentResult(
        status = "some status",
        responseTime = 1.0,
        messages = listOf(
            Message(
                role = "some role",
                content = "some content",
                format = "some format",
                turnId = "some turn id",
                binaryData = null
            )
        ),
        anonymizationEntities = listOf(
            AnonymizationEntity(
                type = "some type",
                value = "some value",
                replacement = "some replacement",
            )
        )
    )

}