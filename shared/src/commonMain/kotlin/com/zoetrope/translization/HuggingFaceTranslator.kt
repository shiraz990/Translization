package com.zoetrope.translization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class HuggingFaceTranslator(private val httpClient: HttpClient) {
    private val apiUrlAR = "https://api-inference.huggingface.co/models/Helsinki-NLP/opus-mt-en-ar"
    private val apiUrlEN = "https://api-inference.huggingface.co/models/Helsinki-NLP/opus-mt-ar-en"
    private val apiKey = getApiKey() // Use expect function to get the API key

    // Configure Json with ignoreUnknownKeys enabled
    private val json = Json {
        ignoreUnknownKeys = true // This ensures unknown JSON keys are ignored
    }
    suspend fun translateToArabic(text: String): String {
        val requestBody = """{"inputs": "$text"}"""

        val headers = mapOf("Authorization" to "Bearer $apiKey")

        /*println("Request URL: $apiUrlAR")
        println("Request Headers: $headers")
        println("Request Body: $requestBody")*/

        val response = httpClient.post(apiUrlAR, headers, requestBody)



        val translationResponse = json.decodeFromString<List<TranslationResponse>>(response.body)
        return translationResponse.firstOrNull()?.generatedText ?: "Translation unavailable"
    }
    suspend fun translateTEnglish(text: String): String {
        val requestBody = """{"inputs": "$text"}"""

        val headers = mapOf("Authorization" to "Bearer $apiKey")

       /* println("Request URL: $apiUrlEN")
        println("Request Headers: $headers")
        println("Request Body: $requestBody")*/

        val response = httpClient.post(apiUrlEN, headers, requestBody)



        val translationResponse = json.decodeFromString<List<TranslationResponse>>(response.body)
        return translationResponse.firstOrNull()?.generatedText ?: "Translation unavailable"
    }
    @Serializable
    data class TranslationResponse(
        @SerialName("translation_text") val generatedText: String
    )

}

// Expect function to be implemented in platform-specific code
expect fun getApiKey(): String
