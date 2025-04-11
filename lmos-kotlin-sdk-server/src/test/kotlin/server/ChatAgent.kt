/*
 * SPDX-FileCopyrightText: Robert Winkler
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.eclipse.lmos.sdk.LMOSContext
import org.eclipse.lmos.sdk.LMOSThingTypes
import org.eclipse.lmos.sdk.agents.ConversationalAgent
import org.eclipse.lmos.sdk.model.AgentEvent
import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult
import org.eclipse.thingweb.reflection.annotations.*
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component


@Thing(id="chatagent", title="Chat Agent",
    description="A chat agent.", type= LMOSThingTypes.AGENT)
@Context(prefix = LMOSContext.prefix, url = LMOSContext.url)
@Link(href = "lmos/capabilities", rel = "service-meta", type = "application/json")
@VersionInfo(instance = "1.0.0")
@Component
class ChatAgent(private val arcAgent: ConversationalAgent): ApplicationListener<SpringApplicationAgentEvent> {

    private val agentEventFlow = MutableSharedFlow<AgentEvent>(replay = 1) // Replay last emitted value

    @Action(title = "Chat", description = "Ask the agent a question.")
    @ActionInput(title = "The question", description = "A question")
    @ActionOutput(title = "The answer", description = "The Answer")
    suspend fun chat(message: AgentRequest) : AgentResult {
        return arcAgent.chat(message)
    }

    @Event(title = "Agent Event", description = "An event from the agent.")
    fun agentEvent() : Flow<AgentEvent> {
        return agentEventFlow
    }

    override fun onApplicationEvent(event: SpringApplicationAgentEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            agentEventFlow.emit(event.event)
        }
    }
}


