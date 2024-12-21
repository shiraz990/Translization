// Android-specific implementation in androidMain
package com.zoetrope.translization

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

actual class HttpClient {
    private val client = OkHttpClient()

    actual suspend fun post(url: String, headers: Map<String, String>, body: String): String {
        val requestBody = body.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(url)
            .apply { headers.forEach { addHeader(it.key, it.value) } }
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected response: $response")
            return response.body?.string() ?: throw IOException("Empty response body")
        }
    }
}
