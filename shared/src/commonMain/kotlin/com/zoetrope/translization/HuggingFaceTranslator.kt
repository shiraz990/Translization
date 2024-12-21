package com.zoetrope.translization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class HuggingFaceTranslator(private val httpClient: HttpClient) {
    private val apiUrl = "https://api-inference.huggingface.co/models/Helsinki-NLP/opus-mt-en-ar"
    private val apiKey = getApiKey() // Use expect function to get the API key

    // Configure Json with ignoreUnknownKeys enabled
    private val json = Json {
        ignoreUnknownKeys = true // This ensures unknown JSON keys are ignored
    }
    suspend fun translate(text: String): String {
        val requestBody = """{"inputs": "$text"}"""
        val headers = mapOf("Authorization" to "Bearer $apiKey")

        val response = httpClient.post(apiUrl, headers, requestBody)

        val translationResponse = json.decodeFromString<List<TranslationResponse>>(response)
        return translationResponse.firstOrNull()?.translationText ?: "Translation failed"
    }

    @Serializable
    data class TranslationResponse(val translationText: String)
}

// Expect function to be implemented in platform-specific code
expect fun getApiKey(): String
