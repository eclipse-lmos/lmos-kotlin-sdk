/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.server

import org.eclipse.lmos.sdk.model.AgentRequest
import org.eclipse.lmos.sdk.model.AgentResult

data class LmosAgent(
    var id: String,
    var title: String,
    var description: String,
    var capabilities: String,
    var chat: (input: AgentRequest) -> AgentResult,
    // var events: Flow<String>
)