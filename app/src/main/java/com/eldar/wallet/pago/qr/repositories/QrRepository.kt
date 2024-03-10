package com.eldar.wallet.pago.qr.repositories

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.eldar.wallet.common.network.DataDownloader
import kotlinx.coroutines.flow.MutableStateFlow

class QrRepository(private val downloader: DataDownloader) {
    private val tag = "QrRepository"

    suspend fun getQr(
        nombre: String,
        apellido: String,
        colorFondo: String = "ffffff",
        colorFrente: String = "000000",
        altura: Int = 128,
        ancho: Int = 128
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