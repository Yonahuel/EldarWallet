package com.eldar.wallet.tarjeta.model.entities

import com.eldar.wallet.login.model.entities.Usuario
import io.realm.kotlin.types.EmbeddedRealmObject

class Tarjeta: EmbeddedRealmObject {
    var numero: String = ""
    var tipo: String = ""
    var codigo: Int = 0
    var vencimiento: String = ""
    var usuario: Usuario? = null
}