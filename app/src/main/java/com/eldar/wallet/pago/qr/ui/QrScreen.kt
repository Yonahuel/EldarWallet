package com.eldar.wallet.pago.qr.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.BarraSuperior
import com.eldar.wallet.viewmodel.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QrScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val usuario by viewModel.usuario.collectAsState()
    val qr by viewModel.qr.collectAsState()

    Scaffold(
        topBar = {
            BarraSuperior(titulo = "Pago con Qr", mostrarBotonAtras = true, navController = navController)
        },
        content = {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 120.dp)
            ) {
                if (qr != null) {
                    Card(
                        modifier = modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(
                            bitmap = qr!!,
                            contentDescription = "QR",
                            modifier = modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Nombre y apellido: ",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${usuario?.nombre} ${usuario?.apellido}"
                )
            }
        }
    )
}

@Preview
@Composable
fun QrScreenPreview() {
    //QrScreen()
}