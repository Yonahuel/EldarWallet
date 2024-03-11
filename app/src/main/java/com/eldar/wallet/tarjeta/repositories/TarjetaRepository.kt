package com.eldar.wallet.tarjeta.repositories

import android.util.Log
import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import io.realm.kotlin.Realm

class TarjetaRepository(
    private val db: Realm
) {
    private val tag = "TarjetaRepository"
    suspend fun insertTarjeta(
        usuario: Usuario,
        numeroTarjeta: String,
        tipoTarjeta: String,
        vencimientoTarjeta: String,
        codigoTarjeta: Int
    ) {
        try {
            db.write {
                val tarjeta = Tarjeta().apply {
                    numero = numeroTarjeta
                    tipo = tipoTarjeta
                    vencimiento = vencimientoTarjeta
                    codigo = codigoTarjeta
                    this.usuario = usuario
                }

                //val usuarioQuery = db.query<Usuario>("id == $0", usuario.id).find()
                val usuarioLive = findLatest(usuario) ?: return@write
                usuarioLive.tarjetas.add(tarjeta)
            }
        } catch (e: Exception) {
            Log.d(tag, e.message ?: "Error al insertar tarjeta")
        }
    }
}