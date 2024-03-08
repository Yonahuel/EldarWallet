package com.eldar.wallet.pago.qr.repositories

import androidx.compose.ui.graphics.ImageBitmap
import com.eldar.wallet.common.network.DataDownloader
import com.eldar.wallet.pago.qr.network.QrApi
import kotlinx.coroutines.flow.MutableStateFlow

class QrRepository(private val downloader: DataDownloader) {
    suspend fun getUrl(
        nombre: String,
        apellido: String
    ): MutableStateFlow<QrApi?> {
        return downloader.downloadQr(nombre = nombre, apellido = apellido)
    }

    suspend fun getUrl2(
        nombre: String,
        apellido: String,
        colorFondo: String = "ffffff",
        colorFrente: String = "000000",
        altura: Int = 128,
        ancho: Int = 128
    ): MutableStateFlow<ImageBitmap> {
        val byteArray = downloader.downloadQr(
            nombre = nombre,
            apellido = apellido,
            colorFondo = colorFondo,
            colorFrente = colorFrente,
            altura = altura,
            ancho = ancho
        )

        val imagen =

        return imagen
    }
}