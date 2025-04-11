/*
 * SPDX-FileCopyrightText: Robert Winkler
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server

import org.eclipse.thingweb.spring.ServientAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


fun main(args: Array<String>) {
    runApplication<AgentApplication>(*args)
}

@SpringBootApplication
@Import(ServientAutoConfiguration::class)
class AgentApplication {

}
