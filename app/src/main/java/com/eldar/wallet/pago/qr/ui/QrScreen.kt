package com.eldar.wallet.pago.qr.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.eldar.wallet.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QrScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val usuario by viewModel.usuario.collectAsState()
    val qr by viewModel.qrUrl.collectAsState()

    Scaffold(
        topBar = {
                 TopAppBar(
                     title = { Text(text = "Pago con QR") },
                     navigationIcon = {
                         IconButton(onClick = { navController.navigateUp() }) {
                             Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Botón atrás")
                         }
                     }
                 )
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                SubcomposeAsyncImage(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    model = qr?.response,
                    contentDescription = "QR") {
                    CircularProgressIndicator(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Nombre y apellido: ", style = MaterialTheme.typography.bodyLarge)
                Text(text = "${usuario?.nombre} ${usuario?.apellido}")
            }
        }
    )
}

@Preview
@Composable
fun QrScreenPreview() {
    //QrScreen()
}