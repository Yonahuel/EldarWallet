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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.BarraSuperior
import com.eldar.wallet.common.ui.navigation.Screen
import com.eldar.wallet.common.ui.theme.AzulAmerican
import com.eldar.wallet.common.ui.theme.DoradoMaster
import com.eldar.wallet.common.ui.theme.RojoVisa
import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import com.eldar.wallet.tarjeta.model.fake.tarjetaFake
import com.eldar.wallet.common.viewmodel.AppViewModel
import io.mockk.mockk

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
            BarraSuperior(titulo = "EldarWallet", mostrarBotonAtras = false, navController = null)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 120.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Saldo
                usuario?.let { it1 -> Saldo(usuario = it1) }
                Spacer(modifier = Modifier.height(16.dp))
                // Botones de acceso
                Row(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    Boton(
                        onClick = { navController.navigate(Screen.Tarjeta.name) },
                        texto = "Agregar tarjeta",
                        modifier = modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Boton(
                        onClick = {
                            viewModel.setQr(nombre = usuario!!.nombre, apellido = usuario!!.apellido)
                            navController.navigate(Screen.Qr.name)
                        },
                        texto = "Pago con QR",
                        modifier = modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Boton(
                        onClick = { navController.navigate(Screen.Nfc.name) },
                        texto = "Generar un pago",
                        modifier = modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Tarjetas asociadas
                if (tarjetas != null) {
                    Text(
                        text = "Tarjetas asociadas:",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(bottom = 16.dp)
                    )
                    LazyRow(modifier = modifier.fillMaxWidth()) {
                        items(tarjetas) { tarjeta ->
                            TarjetaItem(
                                tarjeta = tarjeta,
                                modifier = modifier.padding(8.dp)
                            )
                            Spacer(modifier = modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun Boton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    texto: String
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
    ) {
        Text(text = texto)
    }
}

@Composable
fun Saldo(
    modifier: Modifier = Modifier,
    usuario: Usuario
) {
    var mostrar by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = modifier.padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Saldo:",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = if (mostrar) "$${usuario.saldo}" else "$ *******",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Icon(
                imageVector = if (mostrar) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = "Mostrar saldo",
                modifier = modifier
                    .clickable { mostrar = !mostrar }
                    .padding(start = 24.dp)
            )
        }
    }
}

@Composable
fun TarjetaItem(
    modifier: Modifier = Modifier,
    tarjeta: Tarjeta
) {
    var mostrarNumero by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.wrapContentWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
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
                modifier = modifier.padding(8.dp)
            ) {
                val numero = tarjeta.numero.split(" ")

                Row(
                    modifier = modifier.align(Alignment.Start)
                ) {
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
                //Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = tarjeta.tipo,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = modifier.align(Alignment.Start)
                )
                Row {
                    Text(
                        text =  "CVV",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text =  if (mostrarNumero) "${tarjeta.codigo}" else "***",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = modifier.padding(start = 8.dp)
                    )
                }
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
    TarjetaItem(tarjeta = tarjetaFake)
}