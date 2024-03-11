package com.eldar.wallet.pago.qr.repositories

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.eldar.wallet.common.network.DataDownloader
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Repositorio para obtener códigos QR personalizados.
 * @param downloader Descargador de datos utilizado para obtener el código QR.
 */
class QrRepository(private val downloader: DataDownloader) {
    private val tag = "QrRepository"

    /**
     * Obtiene un código QR con los parámetros especificados.
     *
     * @param nombre Nombre del usuario para el código QR.
     * @param apellido Apellido del usuario para el código QR.
     * @param colorFondo Color de fondo del código QR (opcional, predeterminado: blanco).
     * @param colorFrente Color del frente del código QR (opcional, predeterminado: negro).
     * @param altura Altura del código QR en píxeles (opcional, predeterminado: 750).
     * @param ancho Ancho del código QR en píxeles (opcional, predeterminado: 750).
     * @return [MutableStateFlow] que emite la [ImageBitmap] del código QR generado.
     */
    suspend fun getQr(
        nombre: String,
        apellido: String,
        colorFondo: String = "ffffff",
        colorFrente: String = "000000",
        altura: Int = 750,
        ancho: Int = 750
    ): MutableStateFlow<ImageBitmap?> {
        val imagen = MutableStateFlow<ImageBitmap?>(null)

        try {
            val byteArray = downloader.downloadQr(
                nombre = nombre,
                apellido = apellido,
                colorFondo = colorFondo,
                colorFrente = colorFrente,
                altura = altura,
                ancho = ancho
            )

            BitmapFactory.decodeByteArray(byteArray.value, 0, byteArray.value!!.size, BitmapFactory
                .Options().apply {
                    inMutable = true
                })?.asImageBitmap()?.let {
                imagen.value = it
            }
        } catch (e: Exception) {
            Log.d(tag, "No se pudo obtener la imagen ${e.message}")
        }
        return imagen
    }
}