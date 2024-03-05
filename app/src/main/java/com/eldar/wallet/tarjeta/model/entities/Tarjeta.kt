package com.eldar.wallet.tarjeta.model.entities

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Tarjeta: RealmObject {
    @PrimaryKey
    val numero: Int? = null
    val tipo: String? = null
    val codigo: Int? = null
    val vencimiento: Double? = null
}