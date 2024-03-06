package com.eldar.wallet.login.repositories

import com.eldar.wallet.login.model.entities.Usuario
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.mongodb.kbson.ObjectId

class UsuarioRepository(
    private val db: Realm
) {
    fun getUsuario(
        id: ObjectId
    ): Flow<Usuario?> {

        val usuario = flow {
            emit(
                db
                    .query<Usuario>("id == $id")
                    .first()
                    .find()
            )
        }
        return usuario
    }

    fun getUsuarios(): Flow<List<Usuario>> {
        val usuarios = flow {
            emit(
                db
                    .query<Usuario>()
                    .find()
                    .toList()
            )
        }
        return usuarios
    }

    suspend fun insert(usuario: Usuario) {
        db.write {
            copyToRealm(usuario, UpdatePolicy.ALL)
        }
    }
}