package com.eldar.wallet.common.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.login.model.fake.usuariosFake
import com.eldar.wallet.login.repositories.UsuarioRepository
import com.eldar.wallet.pago.qr.repositories.QrRepository
import com.eldar.wallet.tarjeta.repositories.TarjetaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel compartido.
 *
 * @param application La instancia de la aplicación Android.
 * @param usuarioRepository Repositorio para acceder a los datos de los usuarios.
 * @param qrRepository Repositorio para acceder a los datos del código QR.
 * @param tarjetaRepository Repositorio para acceder a los datos de las tarjetas.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application,
    private val usuarioRepository: UsuarioRepository,
    private val qrRepository: QrRepository,
    private val tarjetaRepository: TarjetaRepository
): AndroidViewModel(application) {
    // Usuario seleccionado
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()
    // Usuarios registrados
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios.asStateFlow()
    // Url de la imagen del qr
    private val _qr = MutableStateFlow<ImageBitmap?>(null)
    val qr = _qr.asStateFlow()

    init {
        viewModelScope.launch {
            // Insertar usuarios falsos (para pruebas)
            usuariosFake.forEach {usuario ->
                usuarioRepository.insert(usuario)
            }
            // Obtener la lista de usuarios
            usuarioRepository.getUsuarios().collect {
                _usuarios.value = it
            }
        }
    }

    /**
     * Establece el usuario seleccionado.
     *
     * @param usuario El usuario seleccionado.
     */
    fun setUsuario(usuario: Usuario) { _usuario.value = usuario }

    /**
     * Genera el código QR para el nombre y apellido especificados.
     *
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     */
    fun setQr(
        nombre: String,
        apellido: String
    ) {
        viewModelScope.launch {
            qrRepository.getQr(nombre = nombre, apellido = apellido).collect {
                _qr.value = it
            }
        }
    }

    // Método para ser usado en una pantalla de registro de usuarios sin implementar
    fun insertUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioRepository.insert(usuario)
        }
    }

    /**
     * Inserta una nueva tarjeta en la base de datos.
     *
     * @param numero Número de la tarjeta.
     * @param codigo Código de seguridad de la tarjeta.
     * @param vencimiento Fecha de vencimiento de la tarjeta.
     * @param tipo Tipo de tarjeta.
     */
    fun insertTarjeta(
        numero: String,
        codigo: Int,
        vencimiento: String,
        tipo: String
    ) {
        if (usuario.value != null) {
            viewModelScope.launch {
                tarjetaRepository.insertTarjeta(
                    usuario = usuario.value!!,
                    numeroTarjeta = numero,
                    codigoTarjeta = codigo,
                    vencimientoTarjeta = vencimiento,
                    tipoTarjeta = tipo
                )
            }
        }
    }
}