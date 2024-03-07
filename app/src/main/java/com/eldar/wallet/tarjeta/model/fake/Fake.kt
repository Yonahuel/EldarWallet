package com.eldar.wallet.tarjeta.model.fake

import com.eldar.wallet.tarjeta.model.entities.Tarjeta

val tarjetaFake = Tarjeta().apply {
    numero = "5031 7557 3453 0604"
    tipo = "Mastercard"
    codigo = 123
    vencimiento = "11/25"
}

val mastercardFake = Tarjeta().apply {
    numero = "5031 7557 3453 0604"
    tipo = "Mastercard"
    codigo = 123
    vencimiento = "11/25"
}

val visaFake = Tarjeta().apply {
    numero = "4509 9535 6623 3704"
    tipo = "Visa"
    codigo = 123
    vencimiento = "11/25"
}

val americanFake = Tarjeta().apply {
    numero = "3711 803032 57522"
    tipo = "American Express"
    codigo = 1234
    vencimiento = "11/25"
}

val tarjetasFake = listOf(
    mastercardFake,
    visaFake,
    americanFake
)