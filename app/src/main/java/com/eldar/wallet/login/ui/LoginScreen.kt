package com.eldar.wallet.login.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.navigation.Screen
import com.eldar.wallet.viewmodel.AppViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var nombreUsuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val usuarios by viewModel.usuarios.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 40.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Iniciar sesión", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it },
                    label = { Text(text = "Nombre de usuario") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Contraseña") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { usuarios.forEach { usuario ->
                    if (usuario.nombreUsuario == nombreUsuario && usuario.password == password) {
                        viewModel.setUsuario(usuario)
                        navController.navigate(Screen.Home.name)
                    }
                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                } }) {
                    Text(text = "Iniciar sesión")
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    //LoginScreen()
}