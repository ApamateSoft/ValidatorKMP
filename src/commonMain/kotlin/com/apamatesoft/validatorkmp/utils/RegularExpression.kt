/**
 * Regular expression patterns for common validations.
 *
 * Each constant is a regex pattern string that can be used with
 * [Validators.regExp] or independently with Kotlin's [Regex] class.
 */
package com.apamatesoft.validatorkmp.utils

object RegularExpression {
    /** Matches one or more digits (0-9). */
    const val NUMBER: String = "^\\d+\$"
    /** Matches one or more English letters (a-z, A-Z). */
    const val ALPHABET: String = "^[a-zA-Z]+\$"
    /** Matches one or more uppercase English letters. */
    const val ALPHABET_UPPERCASE: String = "^[A-Z]+\$"
    /** Matches one or more lowercase English letters. */
    const val ALPHABET_LOWERCASE: String = "^[a-z]+\$"
    /** Matches a single special character or caret/backslash. */
    const val SPECIAL_CHARACTERS: String = "^[^A-z\\s\\d][\\\\\\^]?\$"
    /** Matches one or more Spanish letters (including áéíóúñ and equivalents). */
    const val ALPHABET_ES: String = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+\$"
    /** Matches one or more English alphanumeric characters. */
    const val ALPHA_NUMERIC: String = "^[a-zA-Z0-9]+\$"
    /** Matches one or more Spanish alphanumeric characters. */
    const val ALPHA_NUMERIC_ES: String = "^[a-zA-Z0-9áéíóúüñÁÉÍÓÚÜÑ]+\$"
    /** Matches a proper name in English (letters with optional spaces). */
    const val NAME: String = "^[a-zA-Z]+(\\s?[a-zA-Z])*\$"
    /** Matches a lowercase name (e.g. "maria jose"). */
    const val NAME_LOWERCASE: String = "^[a-z]+(\\s?[a-z])*\$"
    /** Matches an uppercase name (e.g. "JOSE MARIA"). */
    const val NAME_UPPERCASE: String = "^[A-Z]+(\\s?[A-Z])*\$"
    /** Matches a capitalized name (e.g. "Jose Maria"). */
    const val NAME_CAPITALIZE: String = "^[A-Z][a-z]+(\\s?[A-Z][a-z]+)*\$"
    /** Matches a proper name in Spanish. */
    const val NAME_ES: String = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+(\\s?[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ])*\$"
    /** Matches a lowercase Spanish name. */
    const val NAME_LOWERCASE_ES: String = "^[a-záéíóúüñ]+(\\s?[a-záéíóúüñ])*\$"
    /** Matches an uppercase Spanish name. */
    const val NAME_UPPERCASE_ES: String = "^[A-ZÁÉÍÓÚÜÑ]+(\\s?[A-ZÁÉÍÓÚÜÑ])*\$"
    /** Matches a capitalized Spanish name. */
    const val NAME_CAPITALIZE_ES: String = "^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(\\s?[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*\$"
    /** Matches an integer (optionally negative). */
    const val INTEGER: String = "^-?\\d+\$"
    /** Matches a decimal number (optionally negative). */
    const val DECIMAL: String = "^\\-?\\d*\\.?\\d+?\$"
    /** Matches a standard email address. */
    const val EMAIL: String = "^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$"
    /** Matches a link (www, http, or https). */
    const val LINK: String = "^((https://)|(www.)|(http://))+([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*\$"
    /** Matches a www link. */
    const val WWW_LINK: String = "^www.([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*\$"
    /** Matches an http link. */
    const val HTTP_LINK: String = "^http://.([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*\$"
    /** Matches an https link. */
    const val HTTPS_LINK: String = "^https://.([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*\$"
    /** Matches an IPv4 or IPv6 address. */
    const val IP: String = "^((^\\s*((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))\\s*\$)|(^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*\$))\$"
    /** Matches an IPv4 address. */
    const val IPV4: String = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\$"
    /** Matches an IPv6 address. */
    const val IPV6: String = "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\$"
    /** Matches a phone number (international format). */
    const val PHONE: String = "^(\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*\$"
    /** Matches a time in either 12-hour or 24-hour format. */
    const val TIME: String = "^(^(([0-1]?\\d)|(2[0-3])):([0-5][0-9])(:?[0-5][0-9])?\$)|(^((0?[1-9])|(1[0-2])):([0-5][0-9])(:[0-5][0-9])?\\s?([aA]|[pP])[mM]\$)\$"
    /** Matches a time in 12-hour format (e.g. "01:30PM", "1:30 pm"). */
    const val TIME12: String = "^((0?[1-9])|(1[0-2])):([0-5][0-9])(:[0-5][0-9])?\\s?([aA]|[pP])[mM]\$"
    /** Matches a time in 24-hour format (e.g. "14:30", "23:59:59"). */
    const val TIME24: String = "^(([0-1]?\\d)|(2[0-3])):([0-5][0-9])(:?[0-5][0-9])?\$"
    /** Matches a credit card number pattern. */
    const val CARD: String = "(\\d{4}[-. ]?){4}|\\d{4}[-. ]?\\d{6}[-. ]?\\d{5}"
}
