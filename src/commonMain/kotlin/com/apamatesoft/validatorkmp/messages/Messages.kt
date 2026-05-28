package com.apamatesoft.validatorkmp.messages

/**
 * Interface defining default error messages for all predefined validation rules.
 *
 * Implement this interface to provide localized or custom messages.
 * See [MessagesEn] and [MessagesEs] for built-in implementations.
 *
 * Message templates support printf-style format specifiers:
 * - `%d` for integer values
 * - `%s` for string values
 * - `%1$.2f`, `%2$.2f` for formatted decimal values
 */
interface Messages {
    val compareMessage: String
    val dateMessage: String
    val emailMessage: String
    val expirationDateMessage: String
    val httpLinkMessage: String
    val httpsLinkMessage: String
    val ipMessage: String
    val ipv4Message: String
    val ipv6Message: String
    val lengthMessage: String
    val linkMessage: String
    val maxLengthMessage: String
    val maxValueMessage: String
    val minAgeMessage: String
    val minLengthMessage: String
    val minValueMessage: String
    val mustContainMinMessage: String
    val mustContainOneMessage: String
    val nameMessage: String
    val notContainMessage: String
    val numberMessage: String
    val numberPatternMessage: String
    val onlyAlphanumericMessage: String
    val onlyLettersMessage: String
    val onlyNumbersMessage: String
    val rangeLengthMessage: String
    val rangeValueMessage: String
    val regExpMessage: String
    val requiredMessage: String
    val shouldOnlyContainMessage: String
    val timeMessage: String
    val time12Message: String
    val time24Message: String
    val wwwLinkMessage: String
}