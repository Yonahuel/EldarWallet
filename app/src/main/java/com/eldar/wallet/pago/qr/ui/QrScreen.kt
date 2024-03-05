package com.eldar.wallet.pago.qr.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QrScreen() {
    val name = remember { mutableStateOf("John Doe") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pago con QR", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nombre y apellido:", style = MaterialTheme.typography.bodyLarge)
        Text(text = name.value)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Generar QR */ }) {
            Text(text = "Generar QR")
        }
    }
}

@Preview
@Composable
fun QrScreenPreview() {
    QrScreen()
}