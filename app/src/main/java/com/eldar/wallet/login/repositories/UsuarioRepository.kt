package com.eldar.wallet.login.repositories

import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.mongodb.kbson.ObjectId

/**
 * Repositorio para acceder y manipular datos de usuarios.
 * @param db Instancia de Realm utilizada para almacenar los datos.
 */
class UsuarioRepository(
    private val db: Realm
) {
    /**
     * Obtiene una lista de usuarios como un flujo de datos.
     *
     * @return [Flow] que emite una lista de objetos [Usuario].
     */
    fun getUsuarios(): Flow<List<Usuario>> {
        val usuarios = flow {
            emit(
                db.query<Usuario>()
                    .find()
                    .toList()
            )
        }
        return usuarios
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario Objeto [Usuario] a insertar.
     */
    suspend fun insert(usuario: Usuario) {
        db.write {
            copyToRealm(usuario, UpdatePolicy.ALL)
        }
    }
}