package com.zoetrope.translization

actual class PlatformUtils actual constructor() {
    actual fun getPlatform(): String {
        TODO("Not yet implemented")
    }
}

actual class HttpClient {
    actual suspend fun post(
        url: String,
        headers: Map<String, String>,
        body: String
    ): String {
        TODO("Not yet implemented")
    }
}