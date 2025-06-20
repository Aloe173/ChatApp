package com.example.chatapp.data.sender

import com.example.chatapp.data.sender.dto.RegisterDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class Sender {
    private val client = HttpClient(CIO)

    suspend fun send(request: Request): String {
        val url = "http://${request.ip}:${request.port}${request.endpoint}"

        return try {
            client.request(url) {
                method = request.method
                request.headers.forEach { (key, value) ->
                    headers.append(key, value)
                }
                request.body?.let {
                    setBody(it)
                    contentType(ContentType.Application.Json)
                }
            }.bodyAsText()
        } catch (e: Exception) {

            client.close()
            throw e
        }
    }

    fun close() {
        client.close()
    }
}