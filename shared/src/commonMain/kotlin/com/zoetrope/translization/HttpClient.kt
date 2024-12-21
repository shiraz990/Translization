package com.zoetrope.translization
// commonMain - Shared Singleton
// commonMain - Shared code


expect class PlatformUtils() {
    fun getPlatform(): String
}
// commonMain
// Shared code in commonMain

expect class HttpClient {
    suspend fun post(url: String, headers: Map<String, String>, body: String): HttpResponse
}
data class HttpResponse(val code: Int, val body: String)
