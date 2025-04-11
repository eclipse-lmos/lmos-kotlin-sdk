/*
 * SPDX-FileCopyrightText: Robert Winkler
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server

import org.eclipse.lmos.arc.agents.dsl.AllTools
import org.eclipse.lmos.arc.spring.Agents
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AgentConfiguration {

    private val log: Logger = LoggerFactory.getLogger(AgentConfiguration::class.java)

    @Bean
    fun chatArcAgent(agent: Agents) = agent {
        name = "ChatAgent"
        prompt {
            """
            You are a professional digital assistant.  
            
        """.trimIndent()
        }
        model = { "GPT-4o" }
        tools = AllTools
    }

    @Bean
    fun agentEventListener(applicationEventPublisher: ApplicationEventPublisher) =
        ArcEventListener(applicationEventPublisher)

}
