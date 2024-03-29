package com.eldar.wallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eldar.wallet.common.ui.navigation.Screen
import com.eldar.wallet.common.ui.theme.EldarWalletTheme
import com.eldar.wallet.home.ui.HomeScreen
import com.eldar.wallet.login.ui.LoginScreen
import com.eldar.wallet.pago.nfc.ui.NfcScreen
import com.eldar.wallet.pago.qr.ui.QrScreen
import com.eldar.wallet.tarjeta.ui.TarjetaScreen
import com.eldar.wallet.common.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Punto de entrada de la aplicación Android. Anotado con @AndroidEntryPoint para habilitar la inyección de dependencias con Hilt.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //DatabaseEncryption().saveKey("qr_key")

        val viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            EldarWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    content = { MainApp(viewModel = viewModel) }
                )
            }
        }
    }
}

/**
 * Composable principal de la aplicación.
 *
 * @param viewModel ViewModel compartido para la lógica de la aplicación.
 */
@Composable
fun MainApp(
    viewModel: AppViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.name) {
        composable(Screen.Home.name) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Login.name) {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Nfc.name) {
            NfcScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Qr.name) {
            QrScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Tarjeta.name) {
            TarjetaScreen(viewModel = viewModel, navController = navController)
        }
    }
}