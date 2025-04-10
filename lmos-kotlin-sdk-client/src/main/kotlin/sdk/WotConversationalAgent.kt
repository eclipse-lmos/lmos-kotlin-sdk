/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.agents

import io.opentelemetry.instrumentation.annotations.WithSpan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult
import org.eclipse.thingweb.JsonMapper
import org.eclipse.thingweb.Servient
import org.eclipse.thingweb.Wot
import org.eclipse.thingweb.binding.http.HttpProtocolClientFactory
import org.eclipse.thingweb.binding.http.HttpsProtocolClientFactory
import org.eclipse.thingweb.binding.websocket.WebSocketProtocolClientFactory
import org.eclipse.thingweb.thing.ConsumedThing
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass


class WotConversationalAgent private constructor(private val thing : ConsumedThing) :
    ConsumedConversationalAgent {

    private val log : Logger = LoggerFactory.getLogger(ConversationalAgent::class.java)

    companion object {

        suspend fun create(wot: Wot, url: String): ConsumedConversationalAgent {
            return WotConversationalAgent(wot.consume(wot.requestThingDescription(url)) as ConsumedThing)
        }

        suspend fun create(url: String): ConsumedConversationalAgent {
            val wot = Wot.create(
                Servient(clientFactories = listOf(
                HttpProtocolClientFactory(),
                HttpsProtocolClientFactory(),
                WebSocketProtocolClientFactory()
            ))
            )
            return create(wot, url)
        }
    }

    @WithSpan
    override suspend fun chat(message: AgentRequest): AgentResult {
        return try {
            thing.invokeAction(actionName = "chat", input = message)
        } catch (e: Exception) {
            log.error("Failed to receive an answer", e)
            throw e
        }
    }

    override suspend fun <T : Any> consumeEvent(eventName: String, clazz: KClass<T>): Flow<T> {
        return thing.consumeEvent(eventName).map { event ->
            try {
                log.info("Event:" + event.value())
                JsonMapper.instance.treeToValue(event.value(), clazz.java)
            } catch (e: Exception) {
                log.error("Failed to parse event", e)
                throw e
            }
        }
    }
}

suspend inline fun <reified T : Any> ConsumedConversationalAgent.consumeEvent(eventName: String): Flow<T> {
    return consumeEvent(eventName, T::class)
}