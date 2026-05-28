package com.apamatesoft.validatorkmp

import com.apamatesoft.validatorkmp.exceptions.InvalidEvaluationException
import com.apamatesoft.validatorkmp.messages.MessagesEn
import com.apamatesoft.validatorkmp.messages.MessagesEs
import com.apamatesoft.validatorkmp.utils.Alphabets
import com.apamatesoft.validatorkmp.utils.RegularExpression
import com.apamatesoft.validatorkmp.utils.Validators
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ValidatorTest {

    // region Constructor & Companion

    @Test
    fun constructorCreatesInstance() {
        val v = Validator()
        assertNotNull(v)
    }

    @Test
    fun getMessagesReturnsMessagesEnByDefault() {
        Validator.setMessages(MessagesEn())
        assertIs<MessagesEn>(Validator.getMessages())
    }

    @Test
    fun setMessagesChangesMessages() {
        Validator.setMessages(MessagesEs())
        assertIs<MessagesEs>(Validator.getMessages())
        Validator.setMessages(MessagesEn())
    }

    @Test
    fun setMessagesNullDoesNotChangeMessages() {
        Validator.setMessages(MessagesEn())
        Validator.setMessages(null)
        assertNotNull(Validator.getMessages())
    }

    // endregion

    // region validOrFail — required

    @Test
    fun validOrFailRequiredThrowsOnNull() {
        val v = Validator()
        v.required()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    @Test
    fun validOrFailRequiredThrowsOnEmpty() {
        val v = Validator()
        v.required()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", "")
        }
    }

    @Test
    fun validOrFailRequiredPassesOnText() {
        val v = Validator()
        v.required()
        v.validOrFail("key", "text")
    }

    // endregion

    // region validOrFail — exception properties

    @Test
    fun validOrFailExceptionHasCorrectProperties() {
        val v = Validator()
        v.required()
        val ex = assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("myKey", null)
        }
        assertEquals("myKey", ex.key)
        assertEquals(null, ex.value)
    }

    @Test
    fun validOrFailExceptionHasCorrectMessage() {
        val v = Validator()
        v.required("custom required")
        val ex = assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
        assertEquals("custom required", ex.message)
    }

    // endregion

    // region validOrFail — message formatting with parameters

    @Test
    fun validOrFailLengthWithCustomMessage() {
        val v = Validator()
        v.length(5, "Need %d chars")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "abc") }
        assertEquals("Need 5 chars", ex.message)
    }

    @Test
    fun validOrFailMinLengthWithCustomMessage() {
        val v = Validator()
        v.minLength(3, "Min %d")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "ab") }
        assertEquals("Min 3", ex.message)
    }

    @Test
    fun validOrFailMaxLengthWithCustomMessage() {
        val v = Validator()
        v.maxLength(3, "Max %d")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "abcd") }
        assertEquals("Max 3", ex.message)
    }

    @Test
    fun validOrFailRangeLengthWithCustomMessage() {
        val v = Validator()
        v.rangeLength(3, 5, "Between %d and %d")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "ab") }
        assertEquals("Between 3 and 5", ex.message)
    }

    @Test
    fun validOrFailMaxValueWithCustomMessage() {
        val v = Validator()
        v.maxValue(2.5, "Max %1\$.2f")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "3.0") }
        assertEquals("Max 2.50", ex.message)
    }

    @Test
    fun validOrFailMinValueWithCustomMessage() {
        val v = Validator()
        v.minValue(2.5, "Min %1\$.2f")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "1.0") }
        assertEquals("Min 2.50", ex.message)
    }

    @Test
    fun validOrFailRangeValueWithCustomMessage() {
        val v = Validator()
        v.rangeValue(10.0, 30.0, "Range %1\$.2f to %2\$.2f")
        val ex = assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "5") }
        assertEquals("Range 10.00 to 30.00", ex.message)
    }

    // endregion

    // region compareOrFail

    @Test
    fun compareOrFailMatchingStringsPasses() {
        val v = Validator()
        v.required()
        v.compareOrFail("key", "abc", "abc")
    }

    @Test
    fun compareOrFailNonMatchingThrows() {
        val v = Validator()
        v.required()
        assertFailsWith<InvalidEvaluationException> {
            v.compareOrFail("key", "abc", "xyz")
        }
    }

    @Test
    fun compareOrFailEmptyStringsFailsRequired() {
        val v = Validator()
        v.required()
        assertFailsWith<InvalidEvaluationException> {
            v.compareOrFail("key", "", "")
        }
    }

    @Test
    fun compareOrFailDefaultMessage() {
        val v = Validator()
        v.required()
        val ex = assertFailsWith<InvalidEvaluationException> {
            v.compareOrFail("key", "abc", "xyz")
        }
        assertEquals("Not match", ex.message)
    }

    @Test
    fun compareOrFailCustomMessage() {
        val v = Validator()
        v.setNotMatchMessage("custom not match")
        v.required()
        val ex = assertFailsWith<InvalidEvaluationException> {
            v.compareOrFail("key", "abc", "xyz")
        }
        assertEquals("custom not match", ex.message)
    }

    // endregion

    // region Builder

    @Test
    fun builderEmailThrowsOnNull() {
        val v = Validator.Builder().email().build()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", null)
        }
    }

    @Test
    fun builderRequiredPassesOnText() {
        val v = Validator.Builder().required().build()
        v.validOrFail("key", "text")
    }

    @Test
    fun builderMultipleRulesPassOnValid() {
        val v = Validator.Builder().required().email().build()
        v.validOrFail("key", "example@mail.com")
    }

    @Test
    fun builderMultipleRulesFailOnInvalid() {
        val v = Validator.Builder().required().email().build()
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", "abc")
        }
    }

    @Test
    fun builderSetNotMatchMessage() {
        val v = Validator.Builder().setNotMatchMessage("custom").required().build()
        val ex = assertFailsWith<InvalidEvaluationException> {
            v.compareOrFail("key", "a", "b")
        }
        assertEquals("custom", ex.message)
    }

    // endregion

    // region copy()

    @Test
    fun copyMaintainsRules() {
        val v = Validator()
        v.required()
        val copy = v.copy()
        assertFailsWith<InvalidEvaluationException> {
            copy.validOrFail("key", null)
        }
    }

    @Test
    fun copyDoesNotAffectOriginal() {
        val v = Validator()
        v.required()
        val copy = v.copy()
        copy.setNotMatchMessage("changed")
        assertFailsWith<InvalidEvaluationException> {
            v.compareOrFail("key", "a", "b")
        }
    }

    // endregion

    // region All rules with Builder (representative test per rule)

    private fun assertRuleFailsOnNull(rule: Validator.Builder.() -> Validator.Builder) {
        val v = Validator.Builder().apply { rule() }.build()
        assertFailsWith<InvalidEvaluationException> { v.validOrFail("key", null) }
    }

    private fun assertRulePassesOnValid(rule: Validator.Builder.() -> Validator.Builder, input: String) {
        val v = Validator.Builder().apply { rule() }.build()
        v.validOrFail("key", input)
    }

    @Test fun builderRequired() = assertRuleFailsOnNull { required() }
    @Test fun builderRequiredWithMsg() = assertRuleFailsOnNull { required("required!") }
    @Test fun builderLength() { assertRuleFailsOnNull { length(3) } }
    @Test fun builderLengthWithMsg() { assertRuleFailsOnNull { length(3, "Need %d") } }
    @Test fun builderMinLength() = assertRuleFailsOnNull { minLength(3) }
    @Test fun builderMinLengthWithMsg() = assertRuleFailsOnNull { minLength(3, "Min %d") }
    @Test fun builderMaxLength() { val v = Validator.Builder().maxLength(3).build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "abcd") } }
    @Test fun builderMaxLengthWithMsg() { val v = Validator.Builder().maxLength(3, "Max %d").build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "abcd") } }
    @Test fun builderRangeLength() = assertRuleFailsOnNull { rangeLength(3, 5) }
    @Test fun builderRangeLengthWithMsg() = assertRuleFailsOnNull { rangeLength(3, 5, "Between %d and %d") }
    @Test fun builderRegExp() = assertRuleFailsOnNull { regExp(RegularExpression.EMAIL) }
    @Test fun builderRegExpWithMsg() = assertRuleFailsOnNull { regExp(RegularExpression.EMAIL, "bad") }
    @Test fun builderEmail() = assertRuleFailsOnNull { email() }
    @Test fun builderEmailWithMsg() = assertRuleFailsOnNull { email("invalid email") }
    @Test fun builderNumber() = assertRuleFailsOnNull { number() }
    @Test fun builderNumberWithMsg() = assertRuleFailsOnNull { number("not a number") }
    @Test fun builderLink() = assertRuleFailsOnNull { link() }
    @Test fun builderLinkWithMsg() = assertRuleFailsOnNull { link("bad link") }
    @Test fun builderWwwLink() = assertRuleFailsOnNull { wwwLink() }
    @Test fun builderWwwLinkWithMsg() = assertRuleFailsOnNull { wwwLink("bad") }
    @Test fun builderHttpLink() = assertRuleFailsOnNull { httpLink() }
    @Test fun builderHttpLinkWithMsg() = assertRuleFailsOnNull { httpLink("bad") }
    @Test fun builderHttpsLink() = assertRuleFailsOnNull { httpsLink() }
    @Test fun builderHttpsLinkWithMsg() = assertRuleFailsOnNull { httpsLink("bad") }
    @Test fun builderIp() = assertRuleFailsOnNull { ip() }
    @Test fun builderIpWithMsg() = assertRuleFailsOnNull { ip("bad") }
    @Test fun builderIpv4() = assertRuleFailsOnNull { ipv4() }
    @Test fun builderIpv4WithMsg() = assertRuleFailsOnNull { ipv4("bad") }
    @Test fun builderIpv6() = assertRuleFailsOnNull { ipv6() }
    @Test fun builderIpv6WithMsg() = assertRuleFailsOnNull { ipv6("bad") }
    @Test fun builderTime() = assertRuleFailsOnNull { time() }
    @Test fun builderTimeWithMsg() = assertRuleFailsOnNull { time("bad") }
    @Test fun builderTime12() = assertRuleFailsOnNull { time12() }
    @Test fun builderTime12WithMsg() = assertRuleFailsOnNull { time12("bad") }
    @Test fun builderTime24() = assertRuleFailsOnNull { time24() }
    @Test fun builderTime24WithMsg() = assertRuleFailsOnNull { time24("bad") }
    @Test fun builderNumberPattern() = assertRuleFailsOnNull { numberPattern("+xx (xxx) xxx-xx-xx") }
    @Test fun builderNumberPatternWithMsg() = assertRuleFailsOnNull { numberPattern("+xx (xxx) xxx-xx-xx", "bad") }
    @Test fun builderName() = assertRuleFailsOnNull { name() }
    @Test fun builderNameWithMsg() = assertRuleFailsOnNull { name("bad") }
    @Test fun builderOnlyLetters() = assertRuleFailsOnNull { onlyLetters() }
    @Test fun builderOnlyLettersWithMsg() = assertRuleFailsOnNull { onlyLetters("bad") }
    @Test fun builderOnlyAlphanumeric() = assertRuleFailsOnNull { onlyAlphanumeric() }
    @Test fun builderOnlyAlphanumericWithMsg() = assertRuleFailsOnNull { onlyAlphanumeric("bad") }
    @Test fun builderShouldOnlyContain() = assertRuleFailsOnNull { shouldOnlyContain(Alphabets.OCT) }
    @Test fun builderShouldOnlyContainWithMsg() = assertRuleFailsOnNull { shouldOnlyContain(Alphabets.OCT, "bad") }
    @Test fun builderOnlyNumbers() = assertRuleFailsOnNull { onlyNumbers() }
    @Test fun builderOnlyNumbersWithMsg() = assertRuleFailsOnNull { onlyNumbers("bad") }
    @Test fun builderNotContain() = assertRuleFailsOnNull { notContain(Alphabets.OCT) }
    @Test fun builderNotContainWithMsg() = assertRuleFailsOnNull { notContain(Alphabets.OCT, "bad") }
    @Test fun builderMustContainOne() = assertRuleFailsOnNull { mustContainOne(Alphabets.OCT) }
    @Test fun builderMustContainOneWithMsg() = assertRuleFailsOnNull { mustContainOne(Alphabets.OCT, "bad") }
    @Test fun builderMustContainMin() = assertRuleFailsOnNull { mustContainMin(3, Alphabets.ALPHA_LOWERCASE) }
    @Test fun builderMustContainMinWithMsg() = assertRuleFailsOnNull { mustContainMin(3, Alphabets.ALPHA_LOWERCASE, "bad") }
    @Test fun builderMaxValue() { val v = Validator.Builder().maxValue(2.5).build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "3.0") } }
    @Test fun builderMaxValueWithMsg() { val v = Validator.Builder().maxValue(2.5, "Max %1\$.2f").build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "3.0") } }
    @Test fun builderMinValue() { val v = Validator.Builder().minValue(2.5).build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "1.0") } }
    @Test fun builderMinValueWithMsg() { val v = Validator.Builder().minValue(2.5, "Min %1\$.2f").build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "1.0") } }
    @Test fun builderRangeValue() { val v = Validator.Builder().rangeValue(10.0, 30.0).build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "5") } }
    @Test fun builderRangeValueWithMsg() { val v = Validator.Builder().rangeValue(10.0, 30.0, "Range").build(); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "5") } }

    // endregion

    // region All rules via direct instantiation (non-Builder)

    private fun assertDirectRuleFailsOnNull(rule: Validator.() -> Unit) {
        val v = Validator()
        v.rule()
        assertFailsWith<InvalidEvaluationException> { v.validOrFail("key", null) }
    }

    @Test fun directRequired() = assertDirectRuleFailsOnNull { required() }
    @Test fun directRequiredWithMsg() = assertDirectRuleFailsOnNull { required("req!") }
    @Test fun directLength() = assertDirectRuleFailsOnNull { length(3) }
    @Test fun directLengthWithMsg() = assertDirectRuleFailsOnNull { length(3, "Need %d") }
    @Test fun directMinLength() = assertDirectRuleFailsOnNull { minLength(3) }
    @Test fun directMinLengthWithMsg() = assertDirectRuleFailsOnNull { minLength(3, "Min %d") }
    @Test fun directMaxLength() { val v = Validator(); v.maxLength(3); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "abcd") } }
    @Test fun directMaxLengthWithMsg() { val v = Validator(); v.maxLength(3, "Max %d"); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "abcd") } }
    @Test fun directRangeLength() = assertDirectRuleFailsOnNull { rangeLength(3, 5) }
    @Test fun directRangeLengthWithMsg() = assertDirectRuleFailsOnNull { rangeLength(3, 5, "Between") }
    @Test fun directRegExp() = assertDirectRuleFailsOnNull { regExp(RegularExpression.EMAIL) }
    @Test fun directRegExpWithMsg() = assertDirectRuleFailsOnNull { regExp(RegularExpression.EMAIL, "bad") }
    @Test fun directEmail() = assertDirectRuleFailsOnNull { email() }
    @Test fun directEmailWithMsg() = assertDirectRuleFailsOnNull { email("bad") }
    @Test fun directNumber() = assertDirectRuleFailsOnNull { number() }
    @Test fun directNumberWithMsg() = assertDirectRuleFailsOnNull { number("bad") }
    @Test fun directLink() = assertDirectRuleFailsOnNull { link() }
    @Test fun directLinkWithMsg() = assertDirectRuleFailsOnNull { link("bad") }
    @Test fun directWwwLink() = assertDirectRuleFailsOnNull { wwwLink() }
    @Test fun directWwwLinkWithMsg() = assertDirectRuleFailsOnNull { wwwLink("bad") }
    @Test fun directHttpLink() = assertDirectRuleFailsOnNull { httpLink() }
    @Test fun directHttpLinkWithMsg() = assertDirectRuleFailsOnNull { httpLink("bad") }
    @Test fun directHttpsLink() = assertDirectRuleFailsOnNull { httpsLink() }
    @Test fun directHttpsLinkWithMsg() = assertDirectRuleFailsOnNull { httpsLink("bad") }
    @Test fun directIp() = assertDirectRuleFailsOnNull { ip() }
    @Test fun directIpWithMsg() = assertDirectRuleFailsOnNull { ip("bad") }
    @Test fun directIpv4() = assertDirectRuleFailsOnNull { ipv4() }
    @Test fun directIpv4WithMsg() = assertDirectRuleFailsOnNull { ipv4("bad") }
    @Test fun directIpv6() = assertDirectRuleFailsOnNull { ipv6() }
    @Test fun directIpv6WithMsg() = assertDirectRuleFailsOnNull { ipv6("bad") }
    @Test fun directTime() = assertDirectRuleFailsOnNull { time() }
    @Test fun directTimeWithMsg() = assertDirectRuleFailsOnNull { time("bad") }
    @Test fun directTime12() = assertDirectRuleFailsOnNull { time12() }
    @Test fun directTime12WithMsg() = assertDirectRuleFailsOnNull { time12("bad") }
    @Test fun directTime24() = assertDirectRuleFailsOnNull { time24() }
    @Test fun directTime24WithMsg() = assertDirectRuleFailsOnNull { time24("bad") }
    @Test fun directNumberPattern() = assertDirectRuleFailsOnNull { numberPattern("+xx (xxx) xxx-xx-xx") }
    @Test fun directNumberPatternWithMsg() = assertDirectRuleFailsOnNull { numberPattern("+xx (xxx) xxx-xx-xx", "bad") }
    @Test fun directName() = assertDirectRuleFailsOnNull { name() }
    @Test fun directNameWithMsg() = assertDirectRuleFailsOnNull { name("bad") }
    @Test fun directOnlyLetters() = assertDirectRuleFailsOnNull { onlyLetters() }
    @Test fun directOnlyLettersWithMsg() = assertDirectRuleFailsOnNull { onlyLetters("bad") }
    @Test fun directOnlyAlphanumeric() = assertDirectRuleFailsOnNull { onlyAlphanumeric() }
    @Test fun directOnlyAlphanumericWithMsg() = assertDirectRuleFailsOnNull { onlyAlphanumeric("bad") }
    @Test fun directShouldOnlyContain() = assertDirectRuleFailsOnNull { shouldOnlyContain(Alphabets.OCT) }
    @Test fun directShouldOnlyContainWithMsg() = assertDirectRuleFailsOnNull { shouldOnlyContain(Alphabets.OCT, "bad") }
    @Test fun directOnlyNumbers() = assertDirectRuleFailsOnNull { onlyNumbers() }
    @Test fun directOnlyNumbersWithMsg() = assertDirectRuleFailsOnNull { onlyNumbers("bad") }
    @Test fun directNotContain() = assertDirectRuleFailsOnNull { notContain(Alphabets.OCT) }
    @Test fun directNotContainWithMsg() = assertDirectRuleFailsOnNull { notContain(Alphabets.OCT, "bad") }
    @Test fun directMustContainOne() = assertDirectRuleFailsOnNull { mustContainOne(Alphabets.OCT) }
    @Test fun directMustContainOneWithMsg() = assertDirectRuleFailsOnNull { mustContainOne(Alphabets.OCT, "bad") }
    @Test fun directMustContainMin() = assertDirectRuleFailsOnNull { mustContainMin(3, Alphabets.ALPHA_LOWERCASE) }
    @Test fun directMustContainMinWithMsg() = assertDirectRuleFailsOnNull { mustContainMin(3, Alphabets.ALPHA_LOWERCASE, "bad") }
    @Test fun directMaxValue() { val v = Validator(); v.maxValue(2.5); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "3.0") } }
    @Test fun directMaxValueWithMsg() { val v = Validator(); v.maxValue(2.5, "Max"); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "3.0") } }
    @Test fun directMinValue() { val v = Validator(); v.minValue(2.5); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "1.0") } }
    @Test fun directMinValueWithMsg() { val v = Validator(); v.minValue(2.5, "Min"); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "1.0") } }
    @Test fun directRangeValue() { val v = Validator(); v.rangeValue(10.0, 30.0); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "5") } }
    @Test fun directRangeValueWithMsg() { val v = Validator(); v.rangeValue(10.0, 30.0, "Range"); assertFailsWith<InvalidEvaluationException> { v.validOrFail("k", "5") } }

    // endregion

    // region rule (custom)

    @Test
    fun customRuleFailsWhenReturnsFalse() {
        val v = Validator()
        v.rule("custom error") { false }
        assertFailsWith<InvalidEvaluationException> {
            v.validOrFail("key", "anything")
        }
    }

    @Test
    fun customRulePassesWhenReturnsTrue() {
        val v = Validator()
        v.rule("should not appear") { true }
        v.validOrFail("key", "anything")
    }

    // endregion
}