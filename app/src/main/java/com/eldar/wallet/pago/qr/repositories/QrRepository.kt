package com.eldar.wallet.pago.qr.repositories

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
}