package com.apamatesoft.validatorkmp.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AlphabetsTest {

    @Test
    fun bin() = assertEquals("01", Alphabets.BIN)

    @Test
    fun oct() = assertEquals("01234567", Alphabets.OCT)

    @Test
    fun hex() = assertEquals("0123456789aAbBcCdDeEfF", Alphabets.HEX)

    @Test
    fun number() = assertEquals("0123456789", Alphabets.NUMBER)

    @Test
    fun alphabet() = assertEquals("aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ", Alphabets.ALPHABET)

    @Test
    fun alphaLowercase() = assertEquals("abcdefghijklmnopqrstuvwxyz", Alphabets.ALPHA_LOWERCASE)

    @Test
    fun alphaUppercase() = assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", Alphabets.ALPHA_UPPERCASE)

    @Test
    fun alphaNumeric() = assertEquals(Alphabets.NUMBER + Alphabets.ALPHABET, Alphabets.ALPHA_NUMERIC)

    @Test
    fun alphabetEs_containsSpanishChars() {
        val es = Alphabets.ALPHABET_ES
        listOf('ñ', 'Ñ', 'á', 'Á', 'é', 'É', 'í', 'Í', 'ó', 'Ó', 'ú', 'Ú', 'ü', 'Ü').forEach {
            assertTrue(es.contains(it), "ALPHABET_ES should contain '$it'")
        }
    }

    @Test
    fun alphaLowercaseEs() = assertEquals("aábcdeéfghiíjklmnñoópqrstuúüvwxyz", Alphabets.ALPHA_LOWERCASE_ES)

    @Test
    fun alphaUppercaseEs() = assertEquals("AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚÜVWXYZ", Alphabets.ALPHA_UPPERCASE_ES)

    @Test
    fun alphaNumericEs_equalsNumberPlusAlphabetEs() {
        assertEquals(Alphabets.NUMBER + Alphabets.ALPHABET_ES, Alphabets.ALPHA_NUMERIC_ES)
    }
}