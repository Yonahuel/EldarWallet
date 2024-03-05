package com.eldar.wallet.tarjeta.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaScreen(
    modifier: Modifier = Modifier
) {
    var numeroTarjeta by remember { mutableStateOf("") }
    var vencimiento by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Agregar una nueva tarjeta", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = numeroTarjeta,
            onValueChange = { numeroTarjeta = it },
            label = { Text(text = "Número de tarjeta") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = vencimiento,
            onValueChange = { vencimiento = it },
            label = { Text(text = "Fecha de vencimiento") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text(text = "Código de seguridad (CVV)") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Agregar tarjeta */ }) {
            Text(text = "Agregar")
        }
    }
}

@Composable
@Preview
fun TarjetaScreenPreview() {
    TarjetaScreen()
}