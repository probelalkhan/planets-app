package dev.belalkhan.planetsapp.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache5.Apache5
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PlanetsAppHttpClientBuilder {

    private lateinit var protocol: URLProtocol
    private lateinit var host: String

    fun protocol(protocol: URLProtocol) = apply { this.protocol = protocol }

    fun host(host: String) = apply { this.host = host }

    fun build(): MyHttpClient {
        val httpclient = HttpClient(Apache5) {
            expectSuccess = true

            engine {
                followRedirects = true
                socketTimeout = 10_000
                connectTimeout = 10_000
                connectionRequestTimeout = 20_000
            }

            defaultRequest {
                url {
                    protocol = this@PlanetsAppHttpClientBuilder.protocol
                    host = this@PlanetsAppHttpClientBuilder.host
                }

                header(HttpHeaders.ContentType, "application/json")
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
        return MyHttpClient(httpclient)
    }
}
