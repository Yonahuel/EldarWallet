package com.eldar.wallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.login.model.fake.usuariosFake
import com.eldar.wallet.login.repositories.UsuarioRepository
import com.eldar.wallet.pago.qr.network.QrApi
import com.eldar.wallet.pago.qr.repositories.QrRepository
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application,
    private val usuarioRepository: UsuarioRepository,
    private val qrRepository: QrRepository
): AndroidViewModel(application) {
    // Usuario seleccionado
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()
    // Usuarios registrados
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios.asStateFlow()
    // Url de la imagen del qr
    private val _qrUrl = MutableStateFlow<QrApi?>(null)
    val qrUrl = _qrUrl.asStateFlow()

    init {
        viewModelScope.launch {
            usuariosFake.forEach {usuario ->
                usuarioRepository.insert(usuario)
            }
            usuarioRepository.getUsuarios().collect {
                _usuarios.value = it
            }
        }
    }

    fun setUsuario(usuario: Usuario) { _usuario.value = usuario }
    fun setQrUrl(
        nombre: String,
        apellido: String
    ) {
        viewModelScope.launch {
            qrRepository.getUrl(nombre = nombre, apellido = apellido).collect {
                _qrUrl.value = it
            }
        }
    }

    // No se usa
    fun getUsuario(id: ObjectId) {
        viewModelScope.launch {
            usuarioRepository.getUsuario(id).collect {
                _usuario.value = it
            }
        }
    }

    // Método para ser usado en una pantalla de registro de usuarios
    fun insertUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioRepository.insert(usuario)
        }
    }

    fun insertTarjeta(tarjeta: Tarjeta) {
        if (usuario.value != null) {
            usuario.value!!.tarjetas.add(tarjeta)

            viewModelScope.launch {
                usuarioRepository.insert(usuario.value!!)
            }
        }
    }
}