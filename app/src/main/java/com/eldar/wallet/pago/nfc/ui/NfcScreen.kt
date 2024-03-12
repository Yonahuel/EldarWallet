package com.eldar.wallet.pago.nfc.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.home.ui.TarjetaItem
import com.eldar.wallet.common.viewmodel.AppViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import com.eldar.wallet.common.ui.BarraSuperior

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NfcScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val usuario = viewModel.usuario.collectAsState()
    val tarjetas = usuario.value?.tarjetas
    var monto by remember { mutableDoubleStateOf(0.0) }
    var nota by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            BarraSuperior(titulo = "Pago con NFC", mostrarBotonAtras = true, navController = navController)
         },
        content = {
            Column(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp, top = 120.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                if (tarjetas!!.isNotEmpty()) {
                    Text(
                        text = "Tarjetas asociadas",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = modifier.height(8.dp))

                    LazyRow {
                        items(tarjetas) { tarjeta ->
                            TarjetaItem(
                                tarjeta = tarjeta,
                                modifier = modifier.padding(end = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(24.dp))

                    OutlinedTextField(
                        value = monto.toString(),
                        onValueChange = { monto = it.toDouble() },
                        label = { Text(text = "Monto") }
                    )
                    Spacer(modifier = modifier.height(8.dp))

                    OutlinedTextField(
                        value = nota,
                        onValueChange = { nota = it },
                        label = { Text(text = "Nota (opcional)") }
                    )
                    Spacer(modifier = modifier.height(16.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "Pago realizado", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(text = "Realizar pago")
                    }
                } else {
                    Text(
                        text = "No hay tarjetas asociadas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun NfcScreenPreview() {
    //NfcScreen()
}