package com.eldar.wallet.common.network

import android.util.Log
import com.eldar.wallet.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

/**
 * Configuración y creación de un cliente HTTP utilizando Ktor.
 */
private const val TIME_OUT = 60_000

val ktorHttpClient = HttpClient(Android) {
    // Configuración de la negociación de contenido (Content Negotiation)
    install(ContentNegotiation) {
        json()
        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }
    // Configuración del registro de logs
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger ktor =>", message)
            }
        }
        level = LogLevel.ALL
    }
    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }
    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
        header("X-RapidAPI-Key", BuildConfig.RAPID_API_KEY)
        header("X-RapidAPI-Host", "neutrinoapi-qr-code.p.rapidapi.com")
    }
}