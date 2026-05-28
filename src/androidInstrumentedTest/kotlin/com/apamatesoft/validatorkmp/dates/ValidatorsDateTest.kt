package com.apamatesoft.validatorkmp.dates

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorsDateTest {

    // region date

    @Test
    fun dateNull() = assertFalse(validateDate(null, DateFormats.DD_MM_YYYY))

    @Test
    fun dateEmpty() = assertFalse(validateDate("", DateFormats.DD_MM_YYYY))

    @Test
    fun dateText() = assertFalse(validateDate("example", DateFormats.DD_MM_YYYY))

    @Test
    fun dateNoSeparator() = assertFalse(validateDate("21091991", DateFormats.DD_MM_YYYY))

    @Test
    fun dateDashSeparator() = assertFalse(validateDate("21-09-1991", DateFormats.DD_MM_YYYY))

    @Test
    fun dateYyyyMmDdFormat() = assertFalse(validateDate("1991/09/21", DateFormats.DD_MM_YYYY))

    @Test
    fun dateMmDdYyyyFormat() = assertFalse(validateDate("09/21/1991", DateFormats.DD_MM_YYYY))

    @Test
    fun dateValid() = assertTrue(validateDate("21/08/1991", DateFormats.DD_MM_YYYY))

    // endregion

    // region minAge

    @Test
    fun minAgeNull() = assertFalse(validateMinAge(null, DateFormats.DD_MM_YYYY, 18))

    @Test
    fun minAgeEmpty() = assertFalse(validateMinAge("", DateFormats.DD_MM_YYYY, 18))

    @Test
    fun minAgeInvalidFormat() = assertFalse(validateMinAge("example", DateFormats.DD_MM_YYYY, 18))

    @Test
    fun minAgeFutureDate() = assertFalse(validateMinAge("21/09/3000", DateFormats.DD_MM_YYYY, 18))

    @Test
    fun minAgeValid() = assertTrue(validateMinAge("21/09/1991", DateFormats.DD_MM_YYYY, 18))

    // endregion

    // region expirationDate

    @Test
    fun expirationDateNull() = assertFalse(validateExpirationDate(null, DateFormats.DD_MM_YYYY))

    @Test
    fun expirationDateEmpty() = assertFalse(validateExpirationDate("", DateFormats.DD_MM_YYYY))

    @Test
    fun expirationDateInvalidFormat() = assertFalse(validateExpirationDate("example", DateFormats.DD_MM_YYYY))

    @Test
    fun expirationDatePastDate() {
        assertFalse(validateExpirationDate("01/01/2000", DateFormats.DD_MM_YYYY))
    }

    @Test
    fun expirationDateFutureDate() {
        assertTrue(validateExpirationDate("01/01/2099", DateFormats.DD_MM_YYYY))
    }

    // endregion
}