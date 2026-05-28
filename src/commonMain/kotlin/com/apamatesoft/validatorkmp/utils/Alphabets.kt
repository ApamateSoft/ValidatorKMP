package com.apamatesoft.validatorkmp.utils

/**
 * Constant alphabets for use with [Validators.shouldOnlyContain], [Validators.notContain],
 * [Validators.mustContainOne], and [Validators.mustContainMin].
 */
object Alphabets {
    /** Binary characters: `01` */
    const val BIN: String = "01"
    /** Octal characters: `01234567` */
    const val OCT: String = "01234567"
    /** Hexadecimal characters: `0123456789aAbBcCdDeEfF` */
    const val HEX: String = "0123456789aAbBcCdDeEfF"

    /** Numeric characters: `0123456789` */
    const val NUMBER: String = "0123456789"

    /** English alphabet (mixed case): `aAbBcCdDeEfF...` */
    const val ALPHABET: String = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"
    /** English lowercase alphabet: `abcdefghijklmnopqrstuvwxyz` */
    const val ALPHA_LOWERCASE: String = "abcdefghijklmnopqrstuvwxyz"
    /** English uppercase alphabet: `ABCDEFGHIJKLMNOPQRSTUVWXYZ` */
    const val ALPHA_UPPERCASE: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    /** English alphanumeric (numbers + mixed-case alphabet) */
    const val ALPHA_NUMERIC: String = NUMBER + ALPHABET

    /** Spanish alphabet (mixed case, with diacritics and ñ): includes áÁéÉíÍóÓúÚüÜñÑ */
    const val ALPHABET_ES: String = "aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚüÜvVwWxXyYzZ"
    /** Spanish lowercase alphabet: includes á, é, í, ó, ú, ü, ñ */
    const val ALPHA_LOWERCASE_ES: String = "aábcdeéfghiíjklmnñoópqrstuúüvwxyz"
    /** Spanish uppercase alphabet: includes Á, É, Í, Ó, Ú, Ü, Ñ */
    const val ALPHA_UPPERCASE_ES: String = "AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚÜVWXYZ"
    /** Spanish alphanumeric (numbers + mixed-case Spanish alphabet) */
    const val ALPHA_NUMERIC_ES: String = NUMBER + ALPHABET_ES
}