package com.apamatesoft.validatorkmp.exceptions

/**
 * Exception thrown when a validation rule fails during [Validator.validOrFail][com.apamatesoft.validatorkmp.Validator.validOrFail]
 * or [Validator.compareOrFail][com.apamatesoft.validatorkmp.Validator.compareOrFail].
 *
 * @property key identifier of the field that failed validation.
 * @property value the value that was being validated; may be `null`.
 * @param message the error message from the failing rule.
 */
class InvalidEvaluationException(
    val key: String,
    val value: String?,
    message: String
) : Exception(message)