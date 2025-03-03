/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server


import org.eclipse.lmos.sdk.DataSchemaBuilder
import org.eclipse.lmos.sdk.LMOSContext
import org.eclipse.lmos.sdk.LMOSThingTypes
import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult
import org.eclipse.thingweb.Servient
import org.eclipse.thingweb.Wot
import org.eclipse.thingweb.thing.DEFAULT_CONTEXT
import org.eclipse.thingweb.thing.schema.Context
import org.eclipse.thingweb.thing.schema.DataSchema
import org.eclipse.thingweb.thing.schema.VersionInfo
import org.eclipse.thingweb.thing.schema.WoTExposedThing
import org.slf4j.LoggerFactory
import kotlin.reflect.full.createType

class LmosRuntime(private val servient: Servient) {

    private val wot = Wot.create(servient)

    suspend fun start() {
        servient.start()
        log.info("LmosRuntime started")
    }

    suspend fun stop() {
        servient.shutdown()
        log.info("LmosRuntime stopped")
    }

    suspend fun add(agent: LmosAgent)  {
        val agentThing = agent.toThing(wot)
        //servient.addThing(agentThing)
        servient.expose(agentThing.getThingDescription().id)
    }

    // TODO: Remove an agent? Currently not possible in kotlin-wot

    companion object {
        private val log = LoggerFactory.getLogger(LmosRuntime::class.java)
    }

}

private fun LmosAgent.toThing(wot: Wot): WoTExposedThing {
    return wot.produce {
        id = this@toThing.id
        title = this@toThing.title
        description = this@toThing.description
        objectType = LMOSThingTypes.AGENT

        val context = Context(DEFAULT_CONTEXT)
        context.addContext(LMOSContext.prefix, LMOSContext.url)
        objectContext = context

        version = VersionInfo("1.0.0", null) // TODO: Is this something specified by the agent developer? Or LMOS specific?

        // TODO: Links to capabilities

        // TODO: Really <Any, Any> ?
        action<Any, Any>("chat") {
            title = "Chat"
            description = "Chat with the agent"
            safe = false
            idempotent = false
            // TODO: Is this unsafe cast needed? How to avoid it?
            input = DataSchemaBuilder.mapTypeToSchema(AgentRequest::class.createType()) as DataSchema<Any>
            output = DataSchemaBuilder.mapTypeToSchema(AgentResult::class.createType()) as DataSchema<Any>
        }

        // TODO: add agent events
    }
}