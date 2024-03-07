package com.eldar.wallet.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.navigation.Screen
import com.eldar.wallet.common.ui.theme.AzulAmerican
import com.eldar.wallet.common.ui.theme.DoradoMaster
import com.eldar.wallet.common.ui.theme.RojoVisa
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import com.eldar.wallet.tarjeta.model.fake.tarjetaFake
import com.eldar.wallet.viewmodel.AppViewModel
import io.mockk.mockk

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val usuario by viewModel.usuario.collectAsState()
    val tarjetas = usuario?.tarjetas?.toList()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "EldarWallet") })
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                // Saldo
                Text(
                    text = "Saldo:",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$${usuario?.saldo}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tarjetas asociadas
                if (tarjetas != null) {
                    Text(text = "Tarjetas asociadas:", style = MaterialTheme.typography.bodyLarge)
                    LazyRow(modifier = modifier.fillMaxWidth()) {
                        items(tarjetas) { tarjeta ->
                            CardItem(
                                tarjeta = tarjeta,
                                modifier = modifier
                            )
                            Spacer(modifier = modifier.height(12.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Botones de acceso
                Row {
                    Button(
                        onClick = { navController.navigate(Screen.Tarjeta.name) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Agregar una nueva tarjeta")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.setQrUrl(nombre = usuario!!.nombre, apellido = usuario!!.apellido)
                            navController.navigate(Screen.Qr.name)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Pago con QR")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            navController.navigate(Screen.Nfc.name)
                        },
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
    var mostrarNumero by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.wrapContentWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = when(tarjeta.tipo) {
            "Mastercard" -> DoradoMaster
            "Visa" -> RojoVisa
            "American Express" -> AzulAmerican
            else -> Color.White
        })
    ) {
        Row(
            modifier = modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CreditCard,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.padding(start = 16.dp, top = 16.dp)
            )
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                val numero = tarjeta.numero.split(" ")

                Row {
                    Text(
                        text = if (mostrarNumero) tarjeta.numero else "**** **** **** ${numero.last()}",
                        color = Color.White,
                        modifier = modifier.padding(top = 4.dp)
                    )
                    Icon(
                        imageVector = if (mostrarNumero) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = modifier
                            .clickable { mostrarNumero = !mostrarNumero }
                            .padding(start = 16.dp)
                            .align(Alignment.Top)
                    )
                }
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = tarjeta.tipo,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val viewModel = mockk<AppViewModel>()
    val navController = mockk<NavController>()

    HomeScreen(
        viewModel = viewModel,
        navController = navController
    )
}

@Preview
@Composable
fun CardItemPreview() {
    CardItem(tarjeta = tarjetaFake)
}