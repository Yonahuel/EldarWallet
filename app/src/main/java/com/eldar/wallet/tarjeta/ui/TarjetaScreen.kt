package com.eldar.wallet.tarjeta.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import com.eldar.wallet.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                 TopAppBar(
                     title = { Text(text = "Agregar Tarjeta") },
                     navigationIcon = {
                         IconButton(onClick = { navController.navigateUp() }) {
                             Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Botón atrás")
                         }
                     }
                 )
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Agregar una nueva tarjeta", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

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
                        val tarjeta = Tarjeta().apply {
                            numero = numeroNuevo
                            codigo = cvv.toInt()
                            vencimiento = vencimientoNuevo
                            tarjeta = usuario
                            tipo = when(numeroNuevo[0]) {
                                '3' -> "American Express"
                                '4' -> "Visa"
                                '5' -> "Mastercard"
                                else -> {""}
                            }
                        }
                        usuario?.let { viewModel.insertTarjeta(tarjeta) }
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