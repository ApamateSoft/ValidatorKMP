package com.apamatesoft.validatorkmp.exceptions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class InvalidEvaluationExceptionTest {

    @Test
    fun constructorSetsProperties() {
        val ex = InvalidEvaluationException("key1", "value1", "message1")
        assertEquals("key1", ex.key)
        assertEquals("value1", ex.value)
        assertEquals("message1", ex.message)
    }

    @Test
    fun constructorWithNullValue() {
        val ex = InvalidEvaluationException("key2", null, "message2")
        assertEquals("key2", ex.key)
        assertNull(ex.value)
        assertEquals("message2", ex.message)
    }

    @Test
    fun extendsException() {
        val ex = InvalidEvaluationException("k", "v", "m")
        assertIs<Exception>(ex)
    }

    @Test
    fun valueIsNullableString() {
        val withNull: InvalidEvaluationException = InvalidEvaluationException("k", null, "m")
        assertNull(withNull.value)
        val withString: InvalidEvaluationException = InvalidEvaluationException("k", "v", "m")
        assertEquals("v", withString.value)
    }
}