package com.eldar.wallet.common.network

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonNull.content
import org.junit.Assert.assertEquals
import org.junit.Test

class DataDownloaderTest {
    /*@Test
    fun `downloadQr should return image data when HTTP response is successful`() {
        // Arrange
        val httpClient = mockk<HttpClient>()
        val dataDownloader = DataDownloader(httpClient)
        val nombre = "John"
        val apellido = "Doe"
        val colorFondo = "ffffff"
        val colorFrente = "000000"
        val altura = "750"
        val ancho = "750"
        val expectedImageData = "dummy image data".toByteArray()

        runBlocking {
            coEvery { httpClient.post<HttpResponse>(any()) } returns
                    mockk {
                        every { status } returns HttpStatusCode.OK
                        every { content.toByteArray() } returns expectedImageData
                    }
        }

        val result = dataDownloader.downloadQr(nombre, apellido, colorFondo, colorFrente, altura, ancho)

        assertEquals(expectedImageData, result.value)
    }

    @Test
    fun `downloadQr should return null when HTTP response is not successful`() {
        val httpClient = mockk<HttpClient>()
        val dataDownloader = DataDownloader(httpClient)
        val nombre = "John"
        val apellido = "Doe"
        val colorFondo = "ffffff"
        val colorFrente = "000000"
        val altura = 750
        val ancho = 750

        runBlocking {
            coEvery { httpClient.post<HttpResponse>(any()) } returns
                    mockk {
                        every { status } returns HttpStatusCode.BadRequest
                    }
        }

        val result = dataDownloader.downloadQr(nombre, apellido, colorFondo, colorFrente, altura, ancho)

        assertEquals(null, result.value)
    }
     */
}