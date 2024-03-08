package com.eldar.wallet.common.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import io.ktor.util.toByteArray
import kotlinx.coroutines.flow.MutableStateFlow

class DataDownloader {
    private val tag = "DataDownloader"
    private val baseUrl = "https://neutrinoapi-qr-code.p.rapidapi.com"

    @OptIn(InternalAPI::class)
    suspend fun downloadQr(
        httpClient: HttpClient = ktorHttpClient,
        nombre: String,
        apellido: String,
        colorFondo: String,
        colorFrente: String,
        altura: Int,
        ancho: Int
    ): MutableStateFlow<ByteArray?> {
        val data = MutableStateFlow<ByteArray?>(null)
        try {
            val httpResponse = httpClient.post {
                url("${baseUrl}/qr-code")
                body = "bg-color=%23$colorFondo&fg-color=%23$colorFrente&height=$altura&width=$ancho&content=$nombre%20$apellido"
            }
            if (httpResponse.status.isSuccess()) {
                val imagen = httpResponse.content.toByteArray()
                data.value = imagen
            } else {
                Log.d(tag, "Respuesta no exitosa: ${httpResponse.status}")
            }
        } catch (e: ClientRequestException) {
            Log.d(tag, "Error en la solicitud cliente: ${e.message}")
        } catch (e: ServerResponseException) {
            Log.d(tag, "Error en la respuesta del servidor: ${e.message}")
        } catch (e: Exception) {
            Log.d(tag, "Error desconocido: ${e.message ?: "Unknown error"}")
        }
        return data
    }
}