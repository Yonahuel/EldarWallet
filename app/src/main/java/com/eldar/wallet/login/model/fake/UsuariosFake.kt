package com.eldar.wallet.login.model.fake

import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.fake.americanFake
import com.eldar.wallet.tarjeta.model.fake.mastercardFake
import com.eldar.wallet.tarjeta.model.fake.visaFake

val usuariosFake = listOf(
    Usuario().apply {
        nombreUsuario = "user1"
        password = "1234"
        nombre = "John"
        apellido = "Doe"
        saldo = 1000.0
        tarjetas.add(visaFake)
        tarjetas.add(mastercardFake)
        tarjetas.add(americanFake)
    },
    Usuario().apply {
        nombreUsuario = "user2"
        password = "password2"
        nombre = "Jane"
        apellido = "Smith"
        saldo = 1500.0
        tarjetas.add(mastercardFake)
        tarjetas.add(visaFake)
    },
    Usuario().apply {
        nombreUsuario = "user3"
        password = "password3"
        nombre = "Mike"
        apellido = "Johnson"
        saldo = 2000.0
        tarjetas.add(americanFake)
    },
    Usuario().apply {
        nombreUsuario = "user4"
        password = "password4"
        nombre = "Emily"
        apellido = "Brown"
        saldo = 3000.0
        tarjetas.add(visaFake)
    },
    Usuario().apply {
        nombreUsuario = "user5"
        password = "password5"
        nombre = "David"
        apellido = "Martinez"
        saldo = 2500.0
        tarjetas.add(mastercardFake)
    }
)