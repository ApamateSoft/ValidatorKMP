package com.apamatesoft.validatorkmp.messages

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MessagesEsTest {

    private val messages = MessagesEs()

    @Test fun compareMessage() = assertEquals("No coinciden", messages.compareMessage)
    @Test fun dateMessage() = assertEquals("La fecha no coincide con el formato %s", messages.dateMessage)
    @Test fun emailMessage() = assertEquals("Correo electrónico invalido", messages.emailMessage)
    @Test fun expirationDateMessage() = assertEquals("Fecha expirada", messages.expirationDateMessage)
    @Test fun httpLinkMessage() = assertEquals("Enlace http inválido", messages.httpLinkMessage)
    @Test fun httpsLinkMessage() = assertEquals("Enlace https inválido", messages.httpsLinkMessage)
    @Test fun ipMessage() = assertEquals("IP inválida", messages.ipMessage)
    @Test fun ipv4Message() = assertEquals("IPv4 inválida", messages.ipv4Message)
    @Test fun ipv6Message() = assertEquals("IPv6 inválida", messages.ipv6Message)
    @Test fun lengthMessage() = assertEquals("Se requiere %d caracteres", messages.lengthMessage)
    @Test fun linkMessage() = assertEquals("Enlace inválido", messages.linkMessage)
    @Test fun maxLengthMessage() = assertEquals("Se requiere %d o menos caracteres", messages.maxLengthMessage)
    @Test fun maxValueMessage() = assertEquals("El valor no puede ser mayor a %1\$.2f", messages.maxValueMessage)
    @Test fun minAgeMessage() = assertEquals("Se debe tener al menos %d años", messages.minAgeMessage)
    @Test fun minLengthMessage() = assertEquals("Se requiere %d o más caracteres", messages.minLengthMessage)
    @Test fun minValueMessage() = assertEquals("El valor no puede ser menor a %1\$.2f", messages.minValueMessage)
    @Test fun mustContainMinMessage() = assertEquals("Se requiere al menos %d de los siguientes caracteres: %s", messages.mustContainMinMessage)
    @Test fun mustContainOneMessage() = assertEquals("Se requiere al menos uno de los siguientes caracteres: %s", messages.mustContainOneMessage)
    @Test fun nameMessage() = assertEquals("Nombre personal inválido", messages.nameMessage)
    @Test fun notContainMessage() = assertEquals("No se admiten los siguientes caracteres %s", messages.notContainMessage)
    @Test fun numberMessage() = assertEquals("No es un número", messages.numberMessage)
    @Test fun numberPatternMessage() = assertEquals("No coincide con el patrón %s", messages.numberPatternMessage)
    @Test fun onlyAlphanumericMessage() = assertEquals("Solo caracteres alfanuméricos", messages.onlyAlphanumericMessage)
    @Test fun onlyLettersMessage() = assertEquals("Solo letras", messages.onlyLettersMessage)
    @Test fun onlyNumbersMessage() = assertEquals("Solo números", messages.onlyNumbersMessage)
    @Test fun rangeLengthMessage() = assertEquals("El texto debe contener entre %d a %d caracteres", messages.rangeLengthMessage)
    @Test fun rangeValueMessage() = assertEquals("El valor debe estar entre %1\$.2f y %2\$.2f", messages.rangeValueMessage)
    @Test fun regExpMessage() = assertEquals("El valor no coincide con la expresión regular %s", messages.regExpMessage)
    @Test fun requiredMessage() = assertEquals("Requerido", messages.requiredMessage)
    @Test fun shouldOnlyContainMessage() = assertEquals("Solo se admiten los siguientes caracteres %s", messages.shouldOnlyContainMessage)
    @Test fun timeMessage() = assertEquals("Hora inválida", messages.timeMessage)
    @Test fun time12Message() = assertEquals("Formato 12 horas invalido", messages.time12Message)
    @Test fun time24Message() = assertEquals("Formato 24 horas invalido", messages.time24Message)
    @Test fun wwwLinkMessage() = assertEquals("Enlace www inválido", messages.wwwLinkMessage)

    @Test
    fun allPropertiesAreNonNullAndNonEmpty() {
        val msgs = MessagesEs()
        listOf(
            msgs.compareMessage, msgs.dateMessage, msgs.emailMessage, msgs.expirationDateMessage,
            msgs.httpLinkMessage, msgs.httpsLinkMessage, msgs.ipMessage, msgs.ipv4Message,
            msgs.ipv6Message, msgs.lengthMessage, msgs.linkMessage, msgs.maxLengthMessage,
            msgs.maxValueMessage, msgs.minAgeMessage, msgs.minLengthMessage, msgs.minValueMessage,
            msgs.mustContainMinMessage, msgs.mustContainOneMessage, msgs.nameMessage,
            msgs.notContainMessage, msgs.numberMessage, msgs.numberPatternMessage,
            msgs.onlyAlphanumericMessage, msgs.onlyLettersMessage, msgs.onlyNumbersMessage,
            msgs.rangeLengthMessage, msgs.rangeValueMessage, msgs.regExpMessage,
            msgs.requiredMessage, msgs.shouldOnlyContainMessage, msgs.timeMessage,
            msgs.time12Message, msgs.time24Message, msgs.wwwLinkMessage
        ).forEachIndexed { idx, msg ->
            assertTrue(msg.isNotEmpty(), "Message property at index $idx should not be empty")
        }
    }

    @Test
    fun rangeValueMessageContainsSecondParameter() {
        assertTrue(
            MessagesEs().rangeValueMessage.contains("%2\$.2f"),
            "rangeValueMessage should contain %2\$.2f for the max value parameter"
        )
    }
}