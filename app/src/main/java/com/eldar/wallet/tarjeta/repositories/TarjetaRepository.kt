package com.eldar.wallet.tarjeta.repositories

import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
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

            usuario.tarjetas.add(tarjeta)
            copyToRealm(usuario, UpdatePolicy.ALL)

            /*
            val usuarioLive = db.query<Usuario>("id == $0", usuario.id)
                .find()
                .first()

            findLatest(usuarioLive)?.tarjetas?.add(tarjeta)

             */
        }
    }

    suspend fun insertTarjeta2(
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

            db.query<Usuario>(query = "id == $0", usuario.id)
                .first()
                .find()
                ?.let { user ->
                    user.tarjetas
                        .query("numero == $0", numeroTarjeta)
                        .first()
                        .find()
                        ?.let {
                            // Do here the update
                        } ?: user.tarjetas.add(tarjeta)
                }
        }
    }
}