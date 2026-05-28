package com.apamatesoft.validatorkmp.utils

import com.apamatesoft.validatorkmp.dates.DateFormats
import com.apamatesoft.validatorkmp.dates.validateDate
import com.apamatesoft.validatorkmp.dates.validateExpirationDate
import com.apamatesoft.validatorkmp.dates.validateMinAge
import com.apamatesoft.validatorkmp.utils.RegularExpression.ALPHABET
import com.apamatesoft.validatorkmp.utils.RegularExpression.ALPHA_NUMERIC
import com.apamatesoft.validatorkmp.utils.RegularExpression.DECIMAL
import com.apamatesoft.validatorkmp.utils.RegularExpression.EMAIL
import com.apamatesoft.validatorkmp.utils.RegularExpression.HTTP_LINK
import com.apamatesoft.validatorkmp.utils.RegularExpression.HTTPS_LINK
import com.apamatesoft.validatorkmp.utils.RegularExpression.IP
import com.apamatesoft.validatorkmp.utils.RegularExpression.IPV4
import com.apamatesoft.validatorkmp.utils.RegularExpression.IPV6
import com.apamatesoft.validatorkmp.utils.RegularExpression.LINK
import com.apamatesoft.validatorkmp.utils.RegularExpression.NAME
import com.apamatesoft.validatorkmp.utils.RegularExpression.NUMBER
import com.apamatesoft.validatorkmp.utils.RegularExpression.TIME
import com.apamatesoft.validatorkmp.utils.RegularExpression.TIME12
import com.apamatesoft.validatorkmp.utils.RegularExpression.TIME24
import com.apamatesoft.validatorkmp.utils.RegularExpression.WWW_LINK

/**
 * Pure boolean validation functions used by [Validator][com.apamatesoft.validatorkmp.Validator] rules.
 *
 * Each function returns `true` if the value is valid, `false` otherwise.
 * Functions that require a non-null value return `false` when `null` is passed.
 */
object Validators {

    /** Validates that [evaluate] is not `null` or empty. */
    fun required(evaluate: String?): Boolean = !evaluate.isNullOrEmpty()

    /** Validates that [evaluate] has an exact [length] of characters. */
    fun length(evaluate: String?, length: Int): Boolean =
        required(evaluate) && evaluate!!.length == length

    /** Validates that the length of [evaluate] is not less than [min]. */
    fun minLength(evaluate: String?, min: Int): Boolean =
        required(evaluate) && evaluate!!.length >= min

    /** Validates that the length of [evaluate] is not greater than [max]. */
    fun maxLength(evaluate: String?, max: Int): Boolean =
        required(evaluate) && evaluate!!.length <= max

    /** Validates that the length of [evaluate] is in the range [[min], [max]]. */
    fun rangeLength(evaluate: String?, min: Int, max: Int): Boolean =
        required(evaluate) && evaluate!!.length in min..max

    /** Validates that [evaluate] matches the regular expression [regExp]. */
    fun regExp(evaluate: String?, regExp: String): Boolean {
        if (!required(evaluate)) return false
        return Regex(regExp).containsMatchIn(evaluate!!)
    }

    /** Validates that [evaluate] has an email format. */
    fun email(evaluate: String?): Boolean = regExp(evaluate, EMAIL)
    /** Validates that [evaluate] is a numeric format (integers, decimals, and negatives). */
    fun number(evaluate: String?): Boolean = regExp(evaluate, DECIMAL)
    /** Validates that [evaluate] is a link format. */
    fun link(evaluate: String?): Boolean = regExp(evaluate, LINK)
    /** Validates that [evaluate] is a link with www format. */
    fun wwwLink(evaluate: String?): Boolean = regExp(evaluate, WWW_LINK)
    /** Validates that [evaluate] is a link with http format. */
    fun httpLink(evaluate: String?): Boolean = regExp(evaluate, HTTP_LINK)
    /** Validates that [evaluate] is a link with https format. */
    fun httpsLink(evaluate: String?): Boolean = regExp(evaluate, HTTPS_LINK)
    /** Validates that [evaluate] is an IP address (IPv4 or IPv6). */
    fun ip(evaluate: String?): Boolean = regExp(evaluate, IP)
    /** Validates that [evaluate] is an IPv4 address. */
    fun ipv4(evaluate: String?): Boolean = regExp(evaluate, IPV4)
    /** Validates that [evaluate] is an IPv6 address. */
    fun ipv6(evaluate: String?): Boolean = regExp(evaluate, IPV6)
    /** Validates that [evaluate] is a time format (12h or 24h). */
    fun time(evaluate: String?): Boolean = regExp(evaluate, TIME)
    /** Validates that [evaluate] is a time with 12-hour format. */
    fun time12(evaluate: String?): Boolean = regExp(evaluate, TIME12)
    /** Validates that [evaluate] is a time with 24-hour format. */
    fun time24(evaluate: String?): Boolean = regExp(evaluate, TIME24)
    /**
     * Validates that [evaluate] is a proper name.
     *
     * **Note:** Only recognizes English alphabet characters.
     * For other alphabets, use [shouldOnlyContain] with Spanish alphabets from [Alphabets].
     */
    fun name(evaluate: String?): Boolean = regExp(evaluate, NAME)
    /** Validates that [evaluate] contains only numeric characters (`0-9`). */
    fun onlyNumbers(evaluate: String?): Boolean = regExp(evaluate, NUMBER)
    /**
     * Validates that [evaluate] contains only English letters.
     *
     * **Note:** Only recognizes English alphabet characters.
     * For other alphabets, use [shouldOnlyContain].
     */
    fun onlyLetters(evaluate: String?): Boolean = regExp(evaluate, ALPHABET)
    /**
     * Validates that [evaluate] contains only alphanumeric characters (English letters and digits).
     *
     * **Note:** Only recognizes English alphabet characters.
     * For other alphabets, use [shouldOnlyContain].
     */
    fun onlyAlphanumeric(evaluate: String?): Boolean = regExp(evaluate, ALPHA_NUMERIC)

    /**
     * Validates that [evaluate] matches the [pattern], replacing `x` or `X` with numbers.
     *
     * Example valid patterns: `(xxx) xxx xx xx`, `xxx-xx-xxxx`
     *
     * @param evaluate the String to validate; must be non-null.
     * @param pattern the pattern where `x` or `X` are digit placeholders.
     * @return `true` if [evaluate] matches the pattern, `false` otherwise.
     */
    fun numberPattern(evaluate: String?, pattern: String?): Boolean {
        if (evaluate == null || pattern == null) return false
        if (evaluate.length != pattern.length) return false
        for (i in pattern.indices) {
            val pc = pattern[i]
            val ec = evaluate[i]
            if (pc == 'x' || pc == 'X') {
                if (!onlyNumbers(ec.toString()) && pc != ec) return false
            } else {
                if (pc != ec) return false
            }
        }
        return true
    }

    /** Validates that [evaluate] only contains characters included in [alphabet]. */
    fun shouldOnlyContain(evaluate: String?, alphabet: String): Boolean {
        if (!required(evaluate)) return false
        for (a in evaluate!!) if (!alphabet.contains(a)) return false
        return true
    }

    /** Validates that [evaluate] does not contain any character included in [alphabet]. */
    fun notContain(evaluate: String?, alphabet: String): Boolean {
        if (!required(evaluate)) return false
        for (a in alphabet) if (evaluate!!.contains(a)) return false
        return true
    }

    /** Validates that [evaluate] contains at least one character included in [alphabet]. */
    fun mustContainOne(evaluate: String?, alphabet: String): Boolean {
        if (!required(evaluate)) return false
        for (a in alphabet) if (evaluate!!.contains(a)) return true
        return false
    }

    /** Validates that [evaluate] contains at least [min] characters included in [alphabet]. */
    fun mustContainMin(evaluate: String?, min: Int, alphabet: String): Boolean {
        if (!required(evaluate)) return false
        var count = 0
        for (a in evaluate!!) for (b in alphabet) if (a == b) count++
        return count >= min
    }

    /**
     * Validates that the numeric value of [evaluate] is not greater than [condition].
     *
     * **Note:** It is recommended to add a [number] check first to ensure
     * the String is a valid number before using this validator.
     */
    fun maxValue(evaluate: String?, condition: Double): Boolean {
        if (!required(evaluate) || !number(evaluate)) return false
        return evaluate!!.toDouble() <= condition
    }

    /**
     * Validates that the numeric value of [evaluate] is not less than [condition].
     *
     * **Note:** It is recommended to add a [number] check first.
     */
    fun minValue(evaluate: String?, condition: Double): Boolean {
        if (!required(evaluate) || !number(evaluate)) return false
        return evaluate!!.toDouble() >= condition
    }

    /**
     * Validates that the numeric value of [evaluate] is in the range [[min], [max]].
     *
     * **Note:** It is recommended to add a [number] check first.
     */
    fun rangeValue(evaluate: String?, min: Double, max: Double): Boolean {
        if (!required(evaluate) || !number(evaluate)) return false
        val v = evaluate!!.toDouble()
        return v in min..max
    }

    /** Validates that [evaluate] matches the specified date [format]. */
    fun date(evaluate: String?, format: DateFormats): Boolean {
        if (!required(evaluate)) return false
        return validateDate(evaluate!!, format)
    }

    /**
     * Validates that the period from [evaluate] (parsed with [format]) to the current date
     * is greater than or equal to [age] years.
     *
     * **Warning:** This function uses the device's current date.
     * **Note:** It is recommended to add a [date] check first.
     */
    fun minAge(evaluate: String?, format: DateFormats, age: Int): Boolean {
        if (!required(evaluate) || !date(evaluate, format)) return false
        return validateMinAge(evaluate!!, format, age)
    }

    /**
     * Validates that [evaluate] (parsed with [format]) has not expired.
     *
     * **Warning:** This function uses the device's current date.
     * **Note:** It is recommended to add a [date] check first.
     */
    fun expirationDate(evaluate: String?, format: DateFormats): Boolean {
        if (!required(evaluate) || !date(evaluate, format)) return false
        return validateExpirationDate(evaluate!!, format)
    }
}