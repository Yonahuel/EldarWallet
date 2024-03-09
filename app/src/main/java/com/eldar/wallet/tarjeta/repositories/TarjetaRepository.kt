package com.eldar.wallet.tarjeta.repositories

import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class TarjetaRepository(
    private val db: Realm
) {
    suspend fun insertTarjeta(
        usuario: Usuario,
        numeroTarjeta: String,
        tipoTarjeta: String,
        vencimientoTarjeta: String,
        codigoTarjeta: Int
    ) {
        db.write {
            val tarjeta = Tarjeta().apply {
                numero = numeroTarjeta
                tipo = tipoTarjeta
                vencimiento = vencimientoTarjeta
                codigo = codigoTarjeta
                tarjeta = usuario
            }

            val usuarioLive = db.query<Usuario>("id == $0", usuario.id)
                .find()
                .first()

            findLatest(usuarioLive)?.tarjetas?.add(tarjeta)
        }
    }
}