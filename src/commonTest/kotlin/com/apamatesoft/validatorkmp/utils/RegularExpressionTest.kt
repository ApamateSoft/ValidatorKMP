package com.apamatesoft.validatorkmp.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RegularExpressionTest {

    private fun assertMatches(regex: String, input: String) =
        assertTrue(regex.toRegex().matches(input), "Expected '$input' to match '$regex'")

    private fun assertNotMatches(regex: String, input: String) =
        assertFalse(regex.toRegex().matches(input), "Expected '$input' to NOT match '$regex'")

    @Test
    fun number() {
        assertMatches(RegularExpression.NUMBER, "123")
        assertNotMatches(RegularExpression.NUMBER, "12a")
    }

    @Test
    fun alphabet() {
        assertMatches(RegularExpression.ALPHABET, "abc")
        assertNotMatches(RegularExpression.ALPHABET, "abc1")
    }

    @Test
    fun alphabetEs_matchesAccentedChars() {
        assertMatches(RegularExpression.ALPHABET_ES, "José")
        assertNotMatches(RegularExpression.ALPHABET_ES, "Jos1")
    }

    @Test
    fun alphaNumeric() {
        assertMatches(RegularExpression.ALPHA_NUMERIC, "abc123")
        assertNotMatches(RegularExpression.ALPHA_NUMERIC, "abc-123")
    }

    @Test
    fun alphaNumericEs_matchesAccentedChars() {
        assertMatches(RegularExpression.ALPHA_NUMERIC_ES, "José123")
        assertNotMatches(RegularExpression.ALPHA_NUMERIC_ES, "José!123")
    }

    @Test
    fun email() {
        assertMatches(RegularExpression.EMAIL, "example@mail.com")
        assertNotMatches(RegularExpression.EMAIL, "@mail.com")
        assertNotMatches(RegularExpression.EMAIL, "example")
        assertNotMatches(RegularExpression.EMAIL, "example@mail")
        assertNotMatches(RegularExpression.EMAIL, "mail.com")
    }

    @Test
    fun ipv4() {
        assertMatches(RegularExpression.IPV4, "127.0.0.1")
        assertNotMatches(RegularExpression.IPV4, "10.0.0.256")
        assertNotMatches(RegularExpression.IPV4, "10.0.0.0.1")
    }

    @Test
    fun ipv6() {
        assertMatches(RegularExpression.IPV6, "ffff::")
        assertMatches(RegularExpression.IPV6, "ffff::ffff")
        assertMatches(RegularExpression.IPV6, "ffff:ffff::ffff")
        assertNotMatches(RegularExpression.IPV6, "ffff::ffff::ffff")
        assertNotMatches(RegularExpression.IPV6, "fffff::ffff")
        assertNotMatches(RegularExpression.IPV6, "fffg::ffff")
    }

    @Test
    fun time() {
        assertMatches(RegularExpression.TIME, "12:30")
        assertMatches(RegularExpression.TIME, "12:59 am")
        assertMatches(RegularExpression.TIME, "23:59")
        assertMatches(RegularExpression.TIME, "00:00")
        assertNotMatches(RegularExpression.TIME, "25:00")
        assertNotMatches(RegularExpression.TIME, "13:00 am")
        assertNotMatches(RegularExpression.TIME, "1200")
        assertNotMatches(RegularExpression.TIME, "01/01/2020")
    }

    @Test
    fun time12() {
        assertMatches(RegularExpression.TIME12, "12:59 am")
        assertMatches(RegularExpression.TIME12, "1:00 pm")
        assertMatches(RegularExpression.TIME12, "01:00AM")
        assertMatches(RegularExpression.TIME12, "01:00pm")
        assertNotMatches(RegularExpression.TIME12, "23:59")
        assertNotMatches(RegularExpression.TIME12, "00:00")
        assertNotMatches(RegularExpression.TIME12, "13:00 am")
    }

    @Test
    fun time24() {
        assertMatches(RegularExpression.TIME24, "23:59")
        assertMatches(RegularExpression.TIME24, "00:00")
        assertMatches(RegularExpression.TIME24, "13:00")
        assertNotMatches(RegularExpression.TIME24, "12:59 am")
        assertNotMatches(RegularExpression.TIME24, "1:00 pm")
        assertNotMatches(RegularExpression.TIME24, "01:00AM")
        assertNotMatches(RegularExpression.TIME24, "25:00")
    }
}