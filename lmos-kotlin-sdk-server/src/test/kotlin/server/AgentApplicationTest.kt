package org.eclipse.lmos.sdk.server

import kotlinx.coroutines.runBlocking
import org.eclipse.lmos.sdk.agents.WotConversationalAgent
import org.eclipse.lmos.sdk.agents.lastMessage
import org.eclipse.lmos.sdk.agents.toAgentRequest
import org.eclipse.thingweb.Wot
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AgentApplicationTest {

    private val logger = LoggerFactory.getLogger("AgentApplicationTest")

    @Value("\${wot.servient.http.server.port}")
    private var port: Int = 0

    @Autowired
    private lateinit var wot: Wot

//    @Test
//    fun testChat() = runBlocking {
//        val agent = WotConversationalAgent.create(wot, "http://localhost:$port/chatagent")
//        val answer = agent.chat("What is london?".toAgentRequest())
//        logger.info(answer.lastMessage())
//    }
}