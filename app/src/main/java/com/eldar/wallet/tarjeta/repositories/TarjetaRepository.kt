package com.eldar.wallet.tarjeta.repositories

import android.util.Log
import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import io.realm.kotlin.Realm

/**
 * Repositorio para manejar operaciones relacionadas con tarjetas.
 *
 * @param db Instancia de Realm utilizada para almacenar datos.
 */
class TarjetaRepository(
    private val db: Realm
) {
    private val tag = "TarjetaRepository"

    /**
     * Inserta una nueva tarjeta en la base de datos.
     *
     * @param usuario Usuario asociado a la tarjeta.
     * @param numeroTarjeta Número de la tarjeta.
     * @param tipoTarjeta Tipo de la tarjeta (por ejemplo, "Visa", "Mastercard").
     * @param vencimientoTarjeta Fecha de vencimiento de la tarjeta.
     * @param codigoTarjeta Código de seguridad de la tarjeta.
     */
    suspend fun insertTarjeta(
        usuario: Usuario,
        numeroTarjeta: String,
        tipoTarjeta: String,
        vencimientoTarjeta: String,
        codigoTarjeta: Int
    ) {
        val tarjeta = Tarjeta().apply {
            numero = numeroTarjeta
            tipo = tipoTarjeta
            vencimiento = vencimientoTarjeta
            codigo = codigoTarjeta
            this.usuario = usuario
        }
        try {
            db.write {
                val user = usuario ?: return@write
                val usuarioLive = findLatest(user) ?: return@write
                usuarioLive.tarjetas.add(tarjeta)
           }
        } catch (e: Exception) {
            Log.d(tag, e.message ?: "Error al insertar tarjeta")
        }
    }
}