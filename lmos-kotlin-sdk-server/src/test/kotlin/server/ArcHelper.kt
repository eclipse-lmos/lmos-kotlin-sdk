/*
 * SPDX-FileCopyrightText: Robert Winkler
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server


import org.eclipse.lmos.arc.agents.AgentFailedException
import org.eclipse.lmos.arc.agents.User
import org.eclipse.lmos.arc.agents.conversation.AssistantMessage
import org.eclipse.lmos.arc.agents.conversation.Conversation
import org.eclipse.lmos.arc.agents.conversation.latest
import org.eclipse.lmos.arc.agents.conversation.toConversation
import org.eclipse.lmos.arc.core.Result
import org.eclipse.lmos.arc.core.getOrThrow
import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult
import org.eclipse.lmos.sdk.model.Message

suspend fun executeAgent(message: AgentRequest, function: suspend (Conversation) -> Result<Conversation, AgentFailedException>): AgentResult {
    val lastMessage : String = message.messages.last().content
    val assistantMessage = function(lastMessage.toConversation(User("myId"))).getOrThrow().latest<AssistantMessage>()
        ?: throw RuntimeException("No Assistant response")
    return AgentResult(
        messages = listOf(
            Message(
                role = "assistant",
                content = assistantMessage.content,
                turnId = assistantMessage.turnId
            )
        )
    )
}

