package com.eldar.wallet.pago.nfc.ui

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eldar.wallet.home.ui.CardItem
import com.eldar.wallet.tarjeta.model.entities.Tarjeta

@Composable
fun NfcScreen() {
    val tarjetas = remember { mutableStateListOf<Tarjeta>() }
    var monto by remember { mutableStateOf(0.0) }
    var note by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Generar un pago", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Tarjetas asociadas", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        tarjetas.forEach { tarjeta ->
            CardItem(tarjeta = tarjeta)
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = monto.toString(),
            onValueChange = { monto = it.toDouble() },
            label = { Text(text = "Monto") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text(text = "Nota (opcional)") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Realizar pago */ }) {
            Text(text = "Realizar pago")
        }
    }
}

@Preview
@Composable
fun NfcScreenPreview() {
    NfcScreen()
}