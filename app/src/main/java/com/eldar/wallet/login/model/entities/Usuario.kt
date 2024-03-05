package com.eldar.wallet.login.model.entities

import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Usuario: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var nombre: String = ""
    var apellido: String = ""
    var tarjetas: RealmSet<Tarjeta> = realmSetOf()
}