/*
 * SPDX-FileCopyrightText: Robert Winkler
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package ai.ancf.lmos.sdk.model

data class Message(
    val role: String,
    val content: String,
    val format: String = "text",
    val turnId: String? = null,
    val binaryData: List<BinaryData>? = null,
)

class BinaryData(val mimeType: String, val dataAsBase64: String? = null, val source: String? = null)