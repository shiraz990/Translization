package com.zoetrope.translization

import io.github.shiraz990.BuildConfig

actual fun getApiKey(): String {
    return BuildConfig.HUGGING_FACE_API_KEY // Fetch API key from BuildConfig
}
