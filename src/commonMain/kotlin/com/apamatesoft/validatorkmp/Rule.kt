package com.apamatesoft.validatorkmp

/**
 * Function type for custom validation rules.
 *
 * Receives the String to evaluate (may be `null`) and returns `true` if valid.
 */
typealias Validate = (String?) -> Boolean

/**
 * Represents a single validation rule with an error [message] and a [validate] predicate.
 *
 * @property message the error message returned when the rule fails.
 */
internal class Rule(
    val message: String,
    private val validate: Validate
) {
    /**
     * Evaluates [evaluate] against this rule's predicate.
     *
     * @param evaluate the String to validate; may be `null`.
     * @return `true` if the value satisfies the predicate, `false` otherwise.
     */
    fun validate(evaluate: String?): Boolean = validate.invoke(evaluate)
}