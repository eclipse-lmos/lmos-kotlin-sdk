/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.agents


import kotlinx.coroutines.flow.Flow
import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult
import org.eclipse.lmos.sdk.model.Message
import kotlin.reflect.KClass

interface ConversationalAgent {
    suspend fun chat(message: AgentRequest): AgentResult
}

interface ConsumedConversationalAgent: ConversationalAgent {
    suspend fun <T : Any> consumeEvent(eventName: String, clazz: KClass<T>) : Flow<T>
}

fun interface EventListener<T> {
    suspend fun handleEvent(data: T)
}

fun String.toAgentRequest(): AgentRequest {
    return AgentRequest(
        messages = listOf(
            Message(
                role = "user",
                content = this
            )
        )
    )
}

fun String.toAgentResult(): AgentResult {
    return AgentResult(
        messages = listOf(
            Message(
                role = "user",
                content = this
            )
        )
    )
}

fun AgentResult.lastMessage(): String {
    return this.messages.last().content
}


