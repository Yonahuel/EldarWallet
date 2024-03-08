package com.eldar.wallet.common.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.theme.FondoBarraSuperior

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    titulo: String,
    mostrarBotonAtras: Boolean,
    navController: NavController?
) {
    TopAppBar(
        title = { Text(
            text = titulo,
            color = Color.White
        ) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoBarraSuperior),
        navigationIcon = {
            if (mostrarBotonAtras) {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Botón atrás",
                        tint = Color.White
                    )
                }
            }
        }
    )
}