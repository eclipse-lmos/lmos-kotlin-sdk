/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.sdk.model


data class AgentResult(
    val status: String? = null,
    val responseTime: Double = -1.0,
    val messages: List<Message>,
    val anonymizationEntities: List<AnonymizationEntity>? = null,
)
