// Android-specific implementation in androidMain
package com.zoetrope.translization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


actual class HttpClient {
    private val client = OkHttpClient()

    actual suspend fun post(url: String, headers: Map<String, String>, body: String): HttpResponse {
        return withContext(Dispatchers.IO) { // Ensures this block runs on the IO dispatcher
            val requestBody = body.toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(url)
                .apply { headers.forEach { addHeader(it.key, it.value) } }
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: throw IOException("Empty response body")

            // Parse the JSON response to check for errors
            val jsonResponse = Json.parseToJsonElement(responseBody)
            if (jsonResponse is JsonObject && jsonResponse.containsKey("error")) {
                // Extract error message and return it as generatedText
                var errorMessage = ""
                errorMessage= jsonResponse["error"]?.jsonPrimitive?.content.toString()
                return@withContext HttpResponse(response.code, errorMessage)
            }

            // If the response is not an error, return the actual generated text
            HttpResponse(response.code, responseBody)

        }
    }
}
