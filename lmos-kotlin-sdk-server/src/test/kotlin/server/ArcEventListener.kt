/*
 * SPDX-FileCopyrightText: Robert Winkler
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server



import org.eclipse.lmos.arc.agents.events.Event
import org.eclipse.lmos.arc.agents.events.EventHandler
import org.eclipse.lmos.sdk.model.AgentEvent
import org.eclipse.thingweb.JsonMapper
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher

class ArcEventListener(private val applicationEventPublisher: ApplicationEventPublisher) : EventHandler<Event> {

    override fun onEvent(event: Event) {
        applicationEventPublisher.publishEvent(SpringApplicationAgentEvent(
            AgentEvent(
                event::class.simpleName.toString(),
                JsonMapper.instance.writeValueAsString(event),
                event.context["conversationId"],
                event.context["turnId"])
        ))
    }
}

data class SpringApplicationAgentEvent(val event: AgentEvent) : ApplicationEvent(event)