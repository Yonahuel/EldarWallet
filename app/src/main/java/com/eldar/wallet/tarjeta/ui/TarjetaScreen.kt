package com.eldar.wallet.tarjeta.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.BarraSuperior
import com.eldar.wallet.common.ui.navigation.Screen
import com.eldar.wallet.viewmodel.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TarjetaScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var numeroNuevo by remember { mutableStateOf("") }
    var vencimientoNuevo by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    val usuario by viewModel.usuario.collectAsState()

    Scaffold(
        topBar = {
            BarraSuperior(titulo = "Agregar nueva tarjeta", mostrarBotonAtras = true, navController = navController)
        },
        content = {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 120.dp)) {
                OutlinedTextField(
                    value = numeroNuevo,
                    onValueChange = { numeroNuevo = it },
                    label = { Text(text = "Número de tarjeta") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = vencimientoNuevo,
                    onValueChange = { vencimientoNuevo = it },
                    label = { Text(text = "Fecha de vencimiento") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text(text = "Código de seguridad (CVV)") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (cvv != "" && numeroNuevo != "" && vencimientoNuevo != "") {
                        usuario?.let { viewModel.insertTarjeta(
                            numero = numeroNuevo,
                            codigo = cvv.toInt(),
                            vencimiento = vencimientoNuevo,
                            tipo = when(numeroNuevo[0]) {
                                '3' -> "American Express"
                                '4' -> "Visa"
                                '5' -> "Mastercard"
                                else -> {
                                    ""
                                }
                            }
                        ) }
                        navController.navigate(Screen.Home.name)
                        Toast.makeText(context, "Tarjeta guardada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Falta completar datos", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Agregar")
                }
            }
        }
    )
}

@Composable
@Preview
fun TarjetaScreenPreview() {
    //TarjetaScreen()
}