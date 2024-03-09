package com.eldar.wallet.common.network

import com.eldar.wallet.pago.qr.network.QrApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class DataDownloaderTest {
    /*
    private val dataDownloader = mockk<DataDownloader>()
    private val testQr = ByteArray()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun test_downloadQr() = runTest {
        coEvery { dataDownloader.downloadQr(
            nombre = "",
            apellido = "",
            colorFondo = "",
            colorFrente = "",
            altura = 0,
            ancho = 0
        ) } returns MutableStateFlow(testQr)

        dataDownloader.downloadQr(nombre = "", apellido = "").collect() { qr ->
            assertEquals("Test Response", qr?.response)
        }
    }

     */
}