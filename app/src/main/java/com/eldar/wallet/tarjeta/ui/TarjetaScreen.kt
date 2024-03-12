package com.eldar.wallet.tarjeta.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eldar.wallet.common.ui.BarraSuperior
import com.eldar.wallet.common.ui.navigation.Screen
import com.eldar.wallet.tarjeta.model.entities.TipoTarjeta
import com.eldar.wallet.common.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TarjetaScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var numeroNuevo by remember { mutableStateOf("") }
    var vencimientoNuevo by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    val usuario by viewModel.usuario.collectAsState()
    val dateState = rememberDatePickerState()
    val dateDialog = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val calendar = Calendar.getInstance()

    Scaffold(
        topBar = {
            BarraSuperior(titulo = "Agregar nueva tarjeta", mostrarBotonAtras = true, navController = navController)
        },
        content = {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 120.dp)) {
                OutlinedTextField(
                    value = numeroNuevo,
                    onValueChange = { numeroNuevo = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = VisualTransformation { numero ->
                        when (identificarTarjeta(numeroNuevo)) {
                            TipoTarjeta.AMERICAN_EXPRESS -> formatAmex(numero)
                            else -> formatOtrasTarjetas(numero)
                        }

                    },
                    label = { Text(text = "Número de tarjeta") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = vencimientoNuevo,
                    onValueChange = { vencimientoNuevo = it },
                    label = { Text(text = "Fecha de vencimiento") },
                    interactionSource = interactionSource,
                    trailingIcon = {
                        IconButton(onClick = { dateDialog.value = true }) {
                            Icon(imageVector = Icons.Outlined.CalendarMonth, contentDescription = "Elegir fecha")
                        }
                    }
                )
                if (dateDialog.value) {
                    DatePickerDialog(
                        onDismissRequest = {
                            dateDialog.value = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val selectedDate = dateState.selectedDateMillis
                                    calendar.timeInMillis = selectedDate!!
                                    val formattedDate = SimpleDateFormat("MM/yy", Locale.getDefault()).format(calendar.time)
                                    vencimientoNuevo = formattedDate
                                    dateDialog.value = false
                                }
                            ) {
                                Text(text = "Ok")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    dateDialog.value = false
                                }
                            ) {
                                Text(text = "Cancelar")
                            }
                        }
                    ) {
                        DatePicker(
                            state = dateState
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Código de seguridad (CVV)") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (cvv != "" && numeroNuevo != "" && vencimientoNuevo != "") {
                        usuario?.let { viewModel.insertTarjeta(
                            numero = numeroNuevo,
                            codigo = cvv.toInt(),
                            vencimiento = vencimientoNuevo,
                            tipo = when(numeroNuevo[0]) {
                                '3' -> "American Express"
                                '4' -> "Visa"
                                '5' -> "Mastercard"
                                else -> {
                                    ""
                                }
                            }
                        ) }
                        navController.navigate(Screen.Home.name)
                        Toast.makeText(context, "Tarjeta guardada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Falta completar datos", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Agregar")
                }
            }
        }
    )
}

/**
 * Identifica el tipo de tarjeta a partir del número proporcionado.
 *
 * @param cardNumber Número de la tarjeta (sin espacios).
 * @return Tipo de tarjeta (AMERICAN_EXPRESS, VISA, MASTERCARD o DESCONOCIDO).
 */
fun identificarTarjeta(cardNumber: String): TipoTarjeta {
    val ameRegex = Regex("^3[47][0-9]{0,}\$")
    val visaRegex = Regex("^4[0-9]{0,}\$")
    val masterCardRegex = Regex("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{0,}\$")

    val trimmedCardNumber = cardNumber.replace(" ", "")

    return when {
        trimmedCardNumber.matches(ameRegex) -> TipoTarjeta.AMERICAN_EXPRESS
        trimmedCardNumber.matches(visaRegex) -> TipoTarjeta.VISA
        trimmedCardNumber.matches(masterCardRegex) -> TipoTarjeta.MASTERCARD
        else -> TipoTarjeta.DESCONOCIDO
    }
}

/**
 * Formatea el número de una tarjeta American Express (AMEX).
 *
 * @param text AnnotatedString con el número de tarjeta.
 * @return TransformedText con el número formateado.
 */
fun formatAmex(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 15) text.text.substring(0..14) else text.text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i ==3 || i == 9 && i != 14) out += " "
    }

    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 9) return offset + 1
            if(offset <= 15) return offset + 2
            return 17
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 11) return offset - 1
            if(offset <= 17) return offset - 2
            return 15
        }
    }
    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

/**
 * Formatea el número de otras tarjetas (Visa, Mastercard, etc.).
 *
 * @param text AnnotatedString con el número de tarjeta.
 * @return TransformedText con el número formateado.
 */
fun formatOtrasTarjetas(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += " "
    }
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 16) return offset + 3
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 1
            if (offset <= 14) return offset - 2
            if (offset <= 19) return offset - 3
            return 16
        }
    }
    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

@Composable
@Preview
fun TarjetaScreenPreview() {
    //TarjetaScreen()
}