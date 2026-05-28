package com.apamatesoft.validatorkmp

import com.apamatesoft.validatorkmp.dates.DateFormats
import com.apamatesoft.validatorkmp.dates.pattern
import com.apamatesoft.validatorkmp.exceptions.InvalidEvaluationException
import com.apamatesoft.validatorkmp.internal.format
import com.apamatesoft.validatorkmp.messages.Messages
import com.apamatesoft.validatorkmp.messages.MessagesEn
import com.apamatesoft.validatorkmp.utils.Validators

/**
 * Facilitates the validation of Strings by chaining a series of rules.
 *
 * Rules are evaluated in the order they were added. When a rule fails, remaining rules are skipped.
 * A String is considered valid only if it passes all rules.
 *
 * Use [validOrFail] or [compareOrFail] to trigger validation, which throws
 * [InvalidEvaluationException] when a rule fails.
 *
 * @see Validator.Builder
 * @see InvalidEvaluationException
 */
class Validator {

    private val rules: MutableList<Rule> = mutableListOf()
    private var notMatchMessage: String = messages.compareMessage

    /** Creates an empty Validator with no rules. */
    constructor()

    private constructor(builder: Builder) {
        rules.addAll(builder.rules)
        notMatchMessage = builder.notMatchMessage
    }

    companion object {
        @kotlin.concurrent.Volatile
        private var _messages: Messages = MessagesEn()

        /**
         * Sets the default messages for predefined rules.
         * If `null` is passed, the current messages are kept unchanged.
         *
         * @param messages the [Messages] implementation to use, or `null` to keep the current ones.
         */
        fun setMessages(messages: Messages?) {
            if (messages != null) _messages = messages
        }

        /** Returns the current [Messages] implementation used for default rule messages. */
        fun getMessages(): Messages = _messages

        internal val messages: Messages get() = _messages
    }

    /**
     * Validates that [evaluate] passes all rules.
     *
     * Rules are evaluated in order. When a rule fails, an [InvalidEvaluationException] is thrown
     * with the failing rule's message, the [key], and the [evaluate] value.
     *
     * @param key identifier for the field being validated, included in the exception.
     * @param evaluate the String to validate; may be `null`.
     * @throws InvalidEvaluationException if any rule fails.
     */
    @Throws(InvalidEvaluationException::class)
    fun validOrFail(key: String, evaluate: String?) {
        for (rule in rules) {
            if (!rule.validate(evaluate)) {
                throw InvalidEvaluationException(key, evaluate, rule.message)
            }
        }
    }

    /**
     * Validates that [evaluate] and [compare] are equal, then validates [evaluate] against all rules.
     *
     * If the Strings do not match, an [InvalidEvaluationException] is thrown with the
     * [notMatchMessage][setNotMatchMessage].
     *
     * @param key identifier for the field being validated, included in the exception.
     * @param evaluate the String to validate.
     * @param compare the String to compare against.
     * @throws InvalidEvaluationException if the Strings don't match or any rule fails.
     */
    @Throws(InvalidEvaluationException::class)
    fun compareOrFail(key: String, evaluate: String?, compare: String?) {
        if (evaluate != compare) {
            throw InvalidEvaluationException(key, evaluate, notMatchMessage)
        }
        validOrFail(key, evaluate)
    }

    /**
     * Sets the error message used when two Strings compared via [compareOrFail] do not match.
     *
     * @param message the error message for non-matching comparisons.
     */
    fun setNotMatchMessage(message: String) {
        this.notMatchMessage = message
    }

    /**
     * Adds a custom validation rule.
     *
     * @param message error message when the rule fails.
     * @param validate lambda that returns `true` if the value is valid.
     */
    fun rule(message: String, validate: Validate) {
        rules.add(Rule(message, validate))
    }

    /** Validates that the String is not `null` or empty. */
    fun required(message: String) = rule(message, Validators::required)
    /** Validates that the String is not `null` or empty, using the default message. */
    fun required() = required(messages.requiredMessage)

    /** Validates that the String has an exact [length] of characters. */
    fun length(length: Int, message: String) =
        rule(format(message, length)) { Validators.length(it, length) }
    /** Validates that the String has an exact [length] of characters, using the default message. */
    fun length(condition: Int) = length(condition, messages.lengthMessage)

    /** Validates that the String length is not less than [min]. */
    fun minLength(min: Int, message: String) =
        rule(format(message, min)) { Validators.minLength(it, min) }
    /** Validates that the String length is not less than [min], using the default message. */
    fun minLength(min: Int) = minLength(min, messages.minLengthMessage)

    /** Validates that the String length is not greater than [max]. */
    fun maxLength(max: Int, message: String) =
        rule(format(message, max)) { Validators.maxLength(it, max) }
    /** Validates that the String length is not greater than [max], using the default message. */
    fun maxLength(condition: Int) = maxLength(condition, messages.maxLengthMessage)

    /** Validates that the String length is in the range [[min], [max]]. */
    fun rangeLength(min: Int, max: Int, message: String) =
        rule(format(message, min, max)) { Validators.rangeLength(it, min, max) }
    /** Validates that the String length is in the range [[min], [max]], using the default message. */
    fun rangeLength(min: Int, max: Int) = rangeLength(min, max, messages.rangeLengthMessage)

    /** Validates that the String matches the regular expression [regExp]. */
    fun regExp(regExp: String, message: String) =
        rule(format(message, regExp)) { Validators.regExp(it, regExp) }
    /** Validates that the String matches the regular expression [regExp], using the default message. */
    fun regExp(regExp: String) = regExp(regExp, messages.regExpMessage)

    /** Validates that the String has an email format. */
    fun email(message: String) = rule(message, Validators::email)
    /** Validates that the String has an email format, using the default message. */
    fun email() = email(messages.emailMessage)

    /** Validates that the String is a numeric format (integers, decimals, and negatives). */
    fun number(message: String) = rule(message, Validators::number)
    /** Validates that the String is a numeric format, using the default message. */
    fun number() = number(messages.numberMessage)

    /** Validates that the String is a link format. */
    fun link(message: String) = rule(message, Validators::link)
    /** Validates that the String is a link format, using the default message. */
    fun link() = link(messages.linkMessage)

    /** Validates that the String is a link with www format. */
    fun wwwLink(message: String) = rule(message, Validators::wwwLink)
    /** Validates that the String is a link with www format, using the default message. */
    fun wwwLink() = wwwLink(messages.wwwLinkMessage)

    /** Validates that the String is a link with http format. */
    fun httpLink(message: String) = rule(message, Validators::httpLink)
    /** Validates that the String is a link with http format, using the default message. */
    fun httpLink() = httpLink(messages.httpLinkMessage)

    /** Validates that the String is a link with https format. */
    fun httpsLink(message: String) = rule(message, Validators::httpsLink)
    /** Validates that the String is a link with https format, using the default message. */
    fun httpsLink() = httpsLink(messages.httpsLinkMessage)

    /** Validates that the String is an IP format (IPv4 or IPv6). */
    fun ip(message: String) = rule(message, Validators::ip)
    /** Validates that the String is an IP format, using the default message. */
    fun ip() = ip(messages.ipMessage)

    /** Validates that the String is an IPv4 format. */
    fun ipv4(message: String) = rule(message, Validators::ipv4)
    /** Validates that the String is an IPv4 format, using the default message. */
    fun ipv4() = ipv4(messages.ipv4Message)

    /** Validates that the String is an IPv6 format. */
    fun ipv6(message: String) = rule(message, Validators::ipv6)
    /** Validates that the String is an IPv6 format, using the default message. */
    fun ipv6() = ipv6(messages.ipv6Message)

    /** Validates that the String is a time format (12h or 24h). */
    fun time(message: String) = rule(message, Validators::time)
    /** Validates that the String is a time format, using the default message. */
    fun time() = time(messages.timeMessage)

    /** Validates that the String is a time with 12-hour format. */
    fun time12(message: String) = rule(message, Validators::time12)
    /** Validates that the String is a time with 12-hour format, using the default message. */
    fun time12() = time12(messages.time12Message)

    /** Validates that the String is a time with 24-hour format. */
    fun time24(message: String) = rule(message, Validators::time24)
    /** Validates that the String is a time with 24-hour format, using the default message. */
    fun time24() = time24(messages.time24Message)

    /**
     * Validates that the String matches the [pattern], replacing `x` or `X` with numbers.
     *
     * Example valid patterns: `(xxx) xxx xx xx`, `xxx-xx-xxxx`
     */
    fun numberPattern(pattern: String, message: String) =
        rule(format(message, pattern)) { Validators.numberPattern(it, pattern) }
    /** Validates that the String matches the [pattern], replacing `x`/`X` with numbers, using the default message. */
    fun numberPattern(pattern: String) = numberPattern(pattern, messages.numberPatternMessage)

    /**
     * Validates that the String is a proper name.
     *
     * **Note:** Only recognizes English alphabet characters. For other alphabets,
     * use [shouldOnlyContain] with [Alphabets][com.apamatesoft.validatorkmp.utils.Alphabets].
     */
    fun name(message: String) = rule(message, Validators::name)
    /** Validates that the String is a proper name, using the default message. */
    fun name() = name(messages.nameMessage)

    /**
     * Validates that the String contains only letters.
     *
     * **Note:** Only recognizes English alphabet characters. For other alphabets,
     * use [shouldOnlyContain] with [Alphabets][com.apamatesoft.validatorkmp.utils.Alphabets].
     */
    fun onlyLetters(message: String) = rule(message, Validators::onlyLetters)
    /** Validates that the String contains only letters, using the default message. */
    fun onlyLetters() = onlyLetters(messages.onlyLettersMessage)

    /**
     * Validates that the String contains only alphanumeric characters.
     *
     * **Note:** Only recognizes English alphabet characters. For other alphabets,
     * use [shouldOnlyContain] with [Alphabets][com.apamatesoft.validatorkmp.utils.Alphabets].
     */
    fun onlyAlphanumeric(message: String) = rule(message, Validators::onlyAlphanumeric)
    /** Validates that the String contains only alphanumeric characters, using the default message. */
    fun onlyAlphanumeric() = onlyAlphanumeric(messages.onlyAlphanumericMessage)

    /** Validates that the String only contains characters included in [alphabet]. */
    fun shouldOnlyContain(alphabet: String, message: String) =
        rule(format(message, alphabet)) { Validators.shouldOnlyContain(it, alphabet) }
    /** Validates that the String only contains characters included in [alphabet], using the default message. */
    fun shouldOnlyContain(alphabet: String) =
        shouldOnlyContain(alphabet, messages.shouldOnlyContainMessage)

    /** Validates that the String contains only numeric characters. */
    fun onlyNumbers(message: String) = rule(message, Validators::onlyNumbers)
    /** Validates that the String contains only numeric characters, using the default message. */
    fun onlyNumbers() = onlyNumbers(messages.onlyNumbersMessage)

    /** Validates that the String does not contain any character included in [alphabet]. */
    fun notContain(alphabet: String, message: String) =
        rule(format(message, alphabet)) { Validators.notContain(it, alphabet) }
    /** Validates that the String does not contain any character included in [alphabet], using the default message. */
    fun notContain(alphabet: String) = notContain(alphabet, messages.notContainMessage)

    /** Validates that the String contains at least one character included in [alphabet]. */
    fun mustContainOne(alphabet: String, message: String) =
        rule(format(message, alphabet)) { Validators.mustContainOne(it, alphabet) }
    /** Validates that the String contains at least one character included in [alphabet], using the default message. */
    fun mustContainOne(alphabet: String) =
        mustContainOne(alphabet, messages.mustContainOneMessage)

    /** Validates that the String contains at least [min] characters included in [alphabet]. */
    fun mustContainMin(min: Int, alphabet: String, message: String) =
        rule(format(message, min, alphabet)) { Validators.mustContainMin(it, min, alphabet) }
    /** Validates that the String contains at least [min] characters included in [alphabet], using the default message. */
    fun mustContainMin(min: Int, alphabet: String) =
        mustContainMin(min, alphabet, messages.mustContainMinMessage)

    /**
     * Validates that the numeric value of the String is not greater than [max].
     *
     * **Note:** It is recommended to add a [number] rule before this one to ensure
     * the String is a valid number.
     */
    fun maxValue(max: Double, message: String) =
        rule(format(message, max)) { Validators.maxValue(it, max) }
    /** Validates that the numeric value is not greater than [max], using the default message. */
    fun maxValue(max: Double) = maxValue(max, messages.maxValueMessage)

    /**
     * Validates that the numeric value of the String is not less than [condition].
     *
     * **Note:** It is recommended to add a [number] rule before this one to ensure
     * the String is a valid number.
     */
    fun minValue(condition: Double, message: String) =
        rule(format(message, condition)) { Validators.minValue(it, condition) }
    /** Validates that the numeric value is not less than [condition], using the default message. */
    fun minValue(condition: Double) = minValue(condition, messages.minValueMessage)

    /**
     * Validates that the numeric value of the String is in the range [[min], [max]].
     *
     * **Note:** It is recommended to add a [number] rule before this one to ensure
     * the String is a valid number.
     */
    fun rangeValue(min: Double, max: Double, message: String) =
        rule(format(message, min, max)) { Validators.rangeValue(it, min, max) }
    /** Validates that the numeric value is in the range [[min], [max]], using the default message. */
    fun rangeValue(min: Double, max: Double) = rangeValue(min, max, messages.rangeValueMessage)

    /** Validates that the String matches the specified date [format]. */
    fun date(format: DateFormats, message: String) =
        rule(format(message, format.pattern())) { Validators.date(it, format) }
    /** Validates that the String matches the specified date [format], using the default message. */
    fun date(format: DateFormats) = date(format, messages.dateMessage)

    /**
     * Validates that the period from the entered date to the current date is greater than or
     * equal to [age] years.
     *
     * **Warning:** This rule uses the device's current date.
     * **Note:** It is recommended to add a [date] rule before this one to ensure
     * the String is a valid date.
     */
    fun minAge(format: DateFormats, age: Int, message: String) =
        rule(format(message, age)) { Validators.minAge(it, format, age) }
    /** Validates minimum age [age] using [format], using the default message. */
    fun minAge(format: DateFormats, age: Int) = minAge(format, age, messages.minAgeMessage)

    /**
     * Validates that the entered date has not expired.
     *
     * **Warning:** This rule uses the device's current date.
     * **Note:** It is recommended to add a [date] rule before this one to ensure
     * the String is a valid date.
     */
    fun expirationDate(format: DateFormats, message: String) =
        rule(message) { Validators.expirationDate(it, format) }
    /** Validates that the entered date has not expired, using the default message. */
    fun expirationDate(format: DateFormats) =
        expirationDate(format, messages.expirationDateMessage)

    /** Creates a copy of this Validator with the same rules and not-match message. */
    fun copy(): Validator {
        val c = Validator()
        c.rules.addAll(this.rules)
        c.notMatchMessage = this.notMatchMessage
        return c
    }

    /**
     * Builder for constructing a [Validator] in a sequential and centralized way.
     *
     * All rule methods accept an optional `message` parameter. Without it, the default
     * message from [Messages] is used.
     *
     * Example:
     * ```kotlin
     * val validator = Validator.Builder()
     *     .required()
     *     .minLength(6)
     *     .onlyNumbers()
     *     .build()
     * ```
     */
    class Builder {
        internal val rules: MutableList<Rule> = mutableListOf()
        internal var notMatchMessage: String = messages.compareMessage

        /** Sets the error message for when compared Strings don't match in [Validator.compareOrFail]. */
        fun setNotMatchMessage(message: String): Builder { notMatchMessage = message; return this }
        /** Adds a custom validation rule. */
        fun rule(message: String, validate: Validate): Builder { rules.add(Rule(message, validate)); return this }

        /** Validates that the String is not `null` or empty. */
        fun required(message: String): Builder = rule(message, Validators::required)
        /** Validates that the String is not `null` or empty, using the default message. */
        fun required(): Builder = required(messages.requiredMessage)

        /** Validates that the String has an exact [length] of characters. */
        fun length(length: Int, message: String): Builder =
            rule(format(message, length)) { Validators.length(it, length) }
        /** Validates that the String has an exact [length] of characters, using the default message. */
        fun length(length: Int): Builder = length(length, messages.lengthMessage)

        /** Validates that the String length is not less than [min]. */
        fun minLength(min: Int, message: String): Builder =
            rule(format(message, min)) { Validators.minLength(it, min) }
        /** Validates that the String length is not less than [min], using the default message. */
        fun minLength(min: Int): Builder = minLength(min, messages.minLengthMessage)

        /** Validates that the String length is not greater than [max]. */
        fun maxLength(max: Int, message: String): Builder =
            rule(format(message, max)) { Validators.maxLength(it, max) }
        /** Validates that the String length is not greater than [max], using the default message. */
        fun maxLength(max: Int): Builder = maxLength(max, messages.maxLengthMessage)

        /** Validates that the String length is in the range [[min], [max]]. */
        fun rangeLength(min: Int, max: Int, message: String): Builder =
            rule(format(message, min, max)) { Validators.rangeLength(it, min, max) }
        /** Validates that the String length is in the range [[min], [max]], using the default message. */
        fun rangeLength(min: Int, max: Int): Builder = rangeLength(min, max, messages.rangeLengthMessage)

        /** Validates that the String matches the regular expression [regExp]. */
        fun regExp(regExp: String, message: String): Builder =
            rule(format(message, regExp)) { Validators.regExp(it, regExp) }
        /** Validates that the String matches the regular expression [regExp], using the default message. */
        fun regExp(regExp: String): Builder = regExp(regExp, messages.regExpMessage)

        /** Validates that the String has an email format. */
        fun email(message: String): Builder = rule(message, Validators::email)
        /** Validates that the String has an email format, using the default message. */
        fun email(): Builder = email(messages.emailMessage)

        /** Validates that the String is a numeric format. */
        fun number(message: String): Builder = rule(message, Validators::number)
        /** Validates that the String is a numeric format, using the default message. */
        fun number(): Builder = number(messages.numberMessage)

        /** Validates that the String is a link format. */
        fun link(message: String): Builder = rule(message, Validators::link)
        /** Validates that the String is a link format, using the default message. */
        fun link(): Builder = link(messages.linkMessage)

        /** Validates that the String is a link with www format. */
        fun wwwLink(message: String): Builder = rule(message, Validators::wwwLink)
        /** Validates that the String is a link with www format, using the default message. */
        fun wwwLink(): Builder = wwwLink(messages.wwwLinkMessage)

        /** Validates that the String is a link with http format. */
        fun httpLink(message: String): Builder = rule(message, Validators::httpLink)
        /** Validates that the String is a link with http format, using the default message. */
        fun httpLink(): Builder = httpLink(messages.httpLinkMessage)

        /** Validates that the String is a link with https format. */
        fun httpsLink(message: String): Builder = rule(message, Validators::httpsLink)
        /** Validates that the String is a link with https format, using the default message. */
        fun httpsLink(): Builder = httpsLink(messages.httpsLinkMessage)

        /** Validates that the String is an IP format. */
        fun ip(message: String): Builder = rule(message, Validators::ip)
        /** Validates that the String is an IP format, using the default message. */
        fun ip(): Builder = ip(messages.ipMessage)

        /** Validates that the String is an IPv4 format. */
        fun ipv4(message: String): Builder = rule(message, Validators::ipv4)
        /** Validates that the String is an IPv4 format, using the default message. */
        fun ipv4(): Builder = ipv4(messages.ipv4Message)

        /** Validates that the String is an IPv6 format. */
        fun ipv6(message: String): Builder = rule(message, Validators::ipv6)
        /** Validates that the String is an IPv6 format, using the default message. */
        fun ipv6(): Builder = ipv6(messages.ipv6Message)

        /** Validates that the String is a time format. */
        fun time(message: String): Builder = rule(message, Validators::time)
        /** Validates that the String is a time format, using the default message. */
        fun time(): Builder = time(messages.timeMessage)

        /** Validates that the String is a time with 12-hour format. */
        fun time12(message: String): Builder = rule(message, Validators::time12)
        /** Validates that the String is a time with 12-hour format, using the default message. */
        fun time12(): Builder = time12(messages.time12Message)

        /** Validates that the String is a time with 24-hour format. */
        fun time24(message: String): Builder = rule(message, Validators::time24)
        /** Validates that the String is a time with 24-hour format, using the default message. */
        fun time24(): Builder = time24(messages.time24Message)

        /** Validates that the String matches the [pattern], replacing `x`/`X` with numbers. */
        fun numberPattern(pattern: String, message: String): Builder =
            rule(format(message, pattern)) { Validators.numberPattern(it, pattern) }
        /** Validates that the String matches the [pattern], using the default message. */
        fun numberPattern(pattern: String): Builder =
            numberPattern(pattern, messages.numberPatternMessage)

        /** Validates that the String is a proper name. */
        fun name(message: String): Builder = rule(message, Validators::name)
        /** Validates that the String is a proper name, using the default message. */
        fun name(): Builder = name(messages.nameMessage)

        /** Validates that the String contains only letters. */
        fun onlyLetters(message: String): Builder = rule(message, Validators::onlyLetters)
        /** Validates that the String contains only letters, using the default message. */
        fun onlyLetters(): Builder = onlyLetters(messages.onlyLettersMessage)

        /** Validates that the String contains only alphanumeric characters. */
        fun onlyAlphanumeric(message: String): Builder = rule(message, Validators::onlyAlphanumeric)
        /** Validates that the String contains only alphanumeric characters, using the default message. */
        fun onlyAlphanumeric(): Builder = onlyAlphanumeric(messages.onlyAlphanumericMessage)

        /** Validates that the String only contains characters included in [alphabet]. */
        fun shouldOnlyContain(alphabet: String, message: String): Builder =
            rule(format(message, alphabet)) { Validators.shouldOnlyContain(it, alphabet) }
        /** Validates that the String only contains characters included in [alphabet], using the default message. */
        fun shouldOnlyContain(alphabet: String): Builder =
            shouldOnlyContain(alphabet, messages.shouldOnlyContainMessage)

        /** Validates that the String contains only numeric characters. */
        fun onlyNumbers(message: String): Builder = rule(message, Validators::onlyNumbers)
        /** Validates that the String contains only numeric characters, using the default message. */
        fun onlyNumbers(): Builder = onlyNumbers(messages.onlyNumbersMessage)

        /** Validates that the String does not contain any character included in [alphabet]. */
        fun notContain(alphabet: String, message: String): Builder =
            rule(format(message, alphabet)) { Validators.notContain(it, alphabet) }
        /** Validates that the String does not contain any character included in [alphabet], using the default message. */
        fun notContain(alphabet: String): Builder = notContain(alphabet, messages.notContainMessage)

        /** Validates that the String contains at least one character included in [alphabet]. */
        fun mustContainOne(alphabet: String, message: String): Builder =
            rule(format(message, alphabet)) { Validators.mustContainOne(it, alphabet) }
        /** Validates that the String contains at least one character included in [alphabet], using the default message. */
        fun mustContainOne(alphabet: String): Builder =
            mustContainOne(alphabet, messages.mustContainOneMessage)

        /** Validates that the String contains at least [min] characters included in [alphabet]. */
        fun mustContainMin(min: Int, alphabet: String, message: String): Builder =
            rule(format(message, min, alphabet)) { Validators.mustContainMin(it, min, alphabet) }
        /** Validates that the String contains at least [min] characters included in [alphabet], using the default message. */
        fun mustContainMin(min: Int, alphabet: String): Builder =
            mustContainMin(min, alphabet, messages.mustContainMinMessage)

        /** Validates that the numeric value of the String is not greater than [condition]. */
        fun maxValue(condition: Double, message: String): Builder =
            rule(format(message, condition)) { Validators.maxValue(it, condition) }
        /** Validates that the numeric value is not greater than [condition], using the default message. */
        fun maxValue(condition: Double): Builder = maxValue(condition, messages.maxValueMessage)

        /** Validates that the numeric value of the String is not less than [condition]. */
        fun minValue(condition: Double, message: String): Builder =
            rule(format(message, condition)) { Validators.minValue(it, condition) }
        /** Validates that the numeric value is not less than [condition], using the default message. */
        fun minValue(condition: Double): Builder = minValue(condition, messages.minValueMessage)

        /** Validates that the numeric value of the String is in the range [[min], [max]]. */
        fun rangeValue(min: Double, max: Double, message: String): Builder =
            rule(format(message, min, max)) { Validators.rangeValue(it, min, max) }
        /** Validates that the numeric value is in the range [[min], [max]], using the default message. */
        fun rangeValue(min: Double, max: Double): Builder =
            rangeValue(min, max, messages.rangeValueMessage)

        /** Validates that the String matches the specified date [format]. */
        fun date(format: DateFormats, message: String): Builder =
            rule(format(message, format.pattern())) { Validators.date(it, format) }
        /** Validates that the String matches the specified date [format], using the default message. */
        fun date(format: DateFormats): Builder = date(format, messages.dateMessage)

        /** Validates that the age from the entered date is at least [age] years. */
        fun minAge(format: DateFormats, age: Int, message: String): Builder =
            rule(format(message, age)) { Validators.minAge(it, format, age) }
        /** Validates minimum age [age] using [format], using the default message. */
        fun minAge(format: DateFormats, age: Int): Builder =
            minAge(format, age, messages.minAgeMessage)

        /** Validates that the entered date has not expired. */
        fun expirationDate(format: DateFormats, message: String): Builder =
            rule(message) { Validators.expirationDate(it, format) }
        /** Validates that the entered date has not expired, using the default message. */
        fun expirationDate(format: DateFormats): Builder =
            expirationDate(format, messages.expirationDateMessage)

        /** Builds and returns the [Validator] with all configured rules. */
        fun build(): Validator = Validator(this)
    }
}