package com.zoetrope.translization

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform