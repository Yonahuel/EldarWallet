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
    val baseUrl = "https://neutrinoapi-qr-code.p.rapidapi.com"

    /**
     * Descarga un código QR personalizado utilizando los parámetros especificados.
     *
     * @param httpClient Cliente HTTP utilizado para realizar la solicitud (opcional, predeterminado: ktorHttpClient).
     * @param nombre Nombre del usuario para el código QR.
     * @param apellido Apellido del usuario para el código QR.
     * @param colorFondo Color de fondo del código QR en formato hexadecimal (por ejemplo, "ffffff" para blanco).
     * @param colorFrente Color del frente del código QR en formato hexadecimal (por ejemplo, "000000" para negro).
     * @param altura Altura del código QR en píxeles (opcional, predeterminado: 750).
     * @param ancho Ancho del código QR en píxeles (opcional, predeterminado: 750).
     * @return [MutableStateFlow] que emite un arreglo de bytes (imagen del código QR) o null si no se pudo obtener.
     */
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