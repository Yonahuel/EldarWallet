package com.eldar.wallet.pago.nfc.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.home.ui.CardItem
import com.eldar.wallet.viewmodel.AppViewModel

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
            TopAppBar(
                title = { Text(text = "Pago con NFC") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Botón atrás"
                        )
                    }
                }
            )
         },
        content = {
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                Text(text = "Generar un pago", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = modifier.height(16.dp))

                Text(text = "Tarjetas asociadas", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = modifier.height(8.dp))

                tarjetas?.forEach { tarjeta ->
                    CardItem(tarjeta = tarjeta)
                }
                Spacer(modifier = modifier.height(16.dp))

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
                    onClick = { Toast.makeText(context, "Pago realizado", Toast.LENGTH_SHORT).show() }
                ) {
                    Text(text = "Realizar pago")
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