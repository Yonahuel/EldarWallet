package com.eldar.wallet.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import com.eldar.wallet.tarjeta.model.fake.tarjetaFake
import com.eldar.wallet.tarjeta.model.fake.tarjetasFake

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val balance = remember { mutableStateOf(1000.0) }
    val tarjetas = tarjetasFake

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "EldarWallet") })
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                // Saldo
                Text(text = "Saldo:", style = MaterialTheme.typography.bodyLarge)
                Text(text = "$${balance.value}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Tarjetas asociadas
                Text(text = "Tarjetas asociadas:", style = MaterialTheme.typography.bodyLarge)
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tarjetas) { tarjeta ->
                        CardItem(tarjeta = tarjeta)
                        Spacer(modifier = modifier.height(12.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Botones de acceso
                Row {
                    Button(
                        onClick = { /* Agregar una nueva tarjeta */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Agregar una nueva tarjeta")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /* Pago con QR */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Pago con QR")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /* Generar un pago */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Generar un pago")
                    }
                }
            }
        }
    )
}


@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    tarjeta: Tarjeta
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(

        ) {
            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null) // TODO: Cambiar ícono
            Text(text = "**** **** **** ${tarjeta.numero}")
            Text(text = tarjeta.tipo ?: "")
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview
@Composable
fun CardItemPreview() {
    CardItem(tarjeta = tarjetaFake)
}