package com.apamatesoft.validatorkmp

import com.apamatesoft.validatorkmp.dates.DateFormats
import com.apamatesoft.validatorkmp.exceptions.InvalidEvaluationException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ValidatorDateTest {

    // region date

    @Test
    fun dateNullThrows() {
        val v = Validator()
        v.date(DateFormats.DD_MM_YYYY)
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    @Test
    fun dateInvalidThrows() {
        val v = Validator()
        v.date(DateFormats.DD_MM_YYYY)
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", "example")
        }
    }

    @Test
    fun dateValidPasses() {
        val v = Validator()
        v.date(DateFormats.DD_MM_YYYY)
        v.validOrFail("key", "21/08/1991")
    }

    @Test
    fun dateBuilderNullThrows() {
        val v = Validator.Builder().date(DateFormats.DD_MM_YYYY).build()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    // endregion

    // region minAge

    @Test
    fun minAgeNullThrows() {
        val v = Validator()
        v.minAge(DateFormats.DD_MM_YYYY, 18)
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    @Test
    fun minAgeFutureThrows() {
        val v = Validator()
        v.minAge(DateFormats.DD_MM_YYYY, 18)
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", "21/09/3000")
        }
    }

    @Test
    fun minAgeValidPasses() {
        val v = Validator()
        v.minAge(DateFormats.DD_MM_YYYY, 18)
        v.validOrFail("key", "21/09/1991")
    }

    @Test
    fun minAgeBuilderNullThrows() {
        val v = Validator.Builder().minAge(DateFormats.DD_MM_YYYY, 18).build()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    // endregion

    // region expirationDate

    @Test
    fun expirationDateNullThrows() {
        val v = Validator()
        v.expirationDate(DateFormats.DD_MM_YYYY)
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    @Test
    fun expirationDatePastThrows() {
        val v = Validator()
        v.expirationDate(DateFormats.DD_MM_YYYY)
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", "01/01/2000")
        }
    }

    @Test
    fun expirationDateFuturePasses() {
        val v = Validator()
        v.expirationDate(DateFormats.DD_MM_YYYY)
        v.validOrFail("key", "01/01/2099")
    }

    @Test
    fun expirationDateBuilderNullThrows() {
        val v = Validator.Builder().expirationDate(DateFormats.DD_MM_YYYY).build()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    // endregion
}