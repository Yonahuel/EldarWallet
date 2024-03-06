package com.eldar.wallet.common.network

import android.util.Log
import com.eldar.wallet.pago.qr.network.QrApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

class DataDownloader {
    private val tag = "DataDownloader"
    private val baseUrl = "https://neutrinoapi-qr-code.p.rapidapi.com"

    suspend fun downloadQr(
        httpClient: HttpClient = ktorHttpClient,
        nombre: String
    ): MutableStateFlow<QrApi?> {
        val data = MutableStateFlow<QrApi?>(null)
        try {
            val httpResponse = httpClient.get { url("${baseUrl}/qr-code") }
            if (httpResponse.status.isSuccess()) {
                val responseContent = httpResponse.bodyAsText()

                if (responseContent.isNotEmpty()) {
                    val apiResponse = Json.decodeFromString<QrApi>(responseContent)
                    data.value = apiResponse
                } else {
                    Log.d(tag, "La respuesta está vacía")
                }
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