package com.example.chatapp.data.sender

import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json

data class Request (
    val ip: String,
    val port: Int,
    val endpoint: String,
    val method: HttpMethod,
    val headers: Map<String, String>,
    var body: String?
) {
    public inline fun <reified T: Any> jsonBody(data: T) = apply {
        this.body = Json.encodeToString(data)
    }
}