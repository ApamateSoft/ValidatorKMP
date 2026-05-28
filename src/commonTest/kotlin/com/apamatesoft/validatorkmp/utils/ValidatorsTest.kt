package com.apamatesoft.validatorkmp.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorsTest {

    // region required

    @Test fun requiredNull() = assertFalse(Validators.required(null))
    @Test fun requiredEmpty() = assertFalse(Validators.required(""))
    @Test fun requiredSpace() = assertTrue(Validators.required(" "))
    @Test fun requiredText() = assertTrue(Validators.required("xxx"))
    @Test fun requiredNumbers() = assertTrue(Validators.required("123"))
    @Test fun requiredAt() = assertTrue(Validators.required("@nick"))
    @Test fun requiredAtWithDigits() = assertTrue(Validators.required("@nick01"))
    @Test fun requiredAtWithUnderscore() = assertTrue(Validators.required("@nick_01"))

    // endregion

    // region length

    @Test fun lengthNull() = assertFalse(Validators.length(null, 3))
    @Test fun lengthEmpty() = assertFalse(Validators.length("", 3))
    @Test fun lengthTooShort() = assertFalse(Validators.length("12", 3))
    @Test fun lengthExact() = assertTrue(Validators.length("123", 3))
    @Test fun lengthTooLong() = assertFalse(Validators.length("1234", 3))

    // endregion

    // region minLength

    @Test fun minLengthNull() = assertFalse(Validators.minLength(null, 3))
    @Test fun minLengthEmpty() = assertFalse(Validators.minLength("", 3))
    @Test fun lengthBelowMin() = assertFalse(Validators.minLength("12", 3))
    @Test fun lengthAtMin() = assertTrue(Validators.minLength("123", 3))
    @Test fun lengthAboveMin() = assertTrue(Validators.minLength("1234", 3))

    // endregion

    // region maxLength

    @Test fun maxLengthNull() = assertFalse(Validators.maxLength(null, 3))
    @Test fun maxLengthEmpty() = assertFalse(Validators.maxLength("", 3))
    @Test fun maxLengthBelowMax() = assertTrue(Validators.maxLength("1", 3))
    @Test fun maxLengthAtMax() = assertTrue(Validators.maxLength("123", 3))
    @Test fun maxLengthAboveMax() = assertFalse(Validators.maxLength("1234", 3))

    // endregion

    // region rangeLength

    @Test fun rangeLengthNull() = assertFalse(Validators.rangeLength(null, 3, 5))
    @Test fun rangeLengthEmpty() = assertFalse(Validators.rangeLength("", 3, 5))
    @Test fun rangeLengthBelowMin() = assertFalse(Validators.rangeLength("12", 3, 5))
    @Test fun rangeLengthAtMin() = assertTrue(Validators.rangeLength("123", 3, 5))
    @Test fun rangeLengthInRange() = assertTrue(Validators.rangeLength("1234", 3, 5))
    @Test fun rangeLengthAtMax() = assertTrue(Validators.rangeLength("12345", 3, 5))
    @Test fun rangeLengthAboveMax() = assertFalse(Validators.rangeLength("123456", 3, 5))

    // endregion

    // region regExp

    @Test fun regExpNull() = assertFalse(Validators.regExp(null, RegularExpression.EMAIL))
    @Test fun regExpEmpty() = assertFalse(Validators.regExp("", RegularExpression.EMAIL))
    @Test fun regExpNoLocalPart() = assertFalse(Validators.regExp("@mail.com", RegularExpression.EMAIL))
    @Test fun regExpNoDomain() = assertFalse(Validators.regExp("example", RegularExpression.EMAIL))
    @Test fun regExpNoTld() = assertFalse(Validators.regExp("example@mail", RegularExpression.EMAIL))
    @Test fun regExpNoAt() = assertFalse(Validators.regExp("mail.com", RegularExpression.EMAIL))
    @Test fun regExpValidEmail() = assertTrue(Validators.regExp("example@mail.com", RegularExpression.EMAIL))

    // endregion

    // region email

    @Test fun emailNull() = assertFalse(Validators.email(null))
    @Test fun emailEmpty() = assertFalse(Validators.email(""))
    @Test fun emailText() = assertFalse(Validators.email("example"))
    @Test fun emailAtOnly() = assertFalse(Validators.email("@mail"))
    @Test fun emailNoTld() = assertFalse(Validators.email("example@mail."))
    @Test fun emailNoAt() = assertFalse(Validators.email("mail.com"))
    @Test fun emailNoLocal() = assertFalse(Validators.email("@mail.com"))
    @Test fun emailValid() = assertTrue(Validators.email("example@mail.com"))

    // endregion

    // region number

    @Test fun numberNull() = assertFalse(Validators.number(null))
    @Test fun numberEmpty() = assertFalse(Validators.number(""))
    @Test fun numberText() = assertFalse(Validators.number("text"))
    @Test fun numberAlphaSuffix() = assertFalse(Validators.number("a1"))
    @Test fun numberAlphaPrefix() = assertFalse(Validators.number("1a"))
    @Test fun numberComma() = assertFalse(Validators.number("12345,6789"))
    @Test fun numberMultipleDots() = assertFalse(Validators.number("123.456.789"))
    @Test fun numberInteger() = assertTrue(Validators.number("123456789"))
    @Test fun numberNegative() = assertTrue(Validators.number("-123456789"))
    @Test fun numberDecimal() = assertTrue(Validators.number("12345.6789"))
    @Test fun numberNegativeDecimal() = assertTrue(Validators.number("-12345.6789"))
    @Test fun numberSingleDigit() = assertTrue(Validators.number("1"))

    // endregion

    // region link

    @Test fun linkNull() = assertFalse(Validators.link(null))
    @Test fun linkEmpty() = assertFalse(Validators.link(""))
    @Test fun linkBareDomain() = assertFalse(Validators.link("google.com"))
    @Test fun linkText() = assertFalse(Validators.link("text"))
    @Test fun linkWww() = assertTrue(Validators.link("www.google.com"))
    @Test fun linkHttp() = assertTrue(Validators.link("http://google.com"))
    @Test fun linkHttps() = assertTrue(Validators.link("https://google.com"))
    @Test fun linkHttpWithQuery() = assertTrue(Validators.link("http://google.com/api/auth?name=Name&lastName=LastName"))

    // endregion

    // region wwwLink

    @Test fun wwwLinkNull() = assertFalse(Validators.wwwLink(null))
    @Test fun wwwLinkEmpty() = assertFalse(Validators.wwwLink(""))
    @Test fun wwwLinkBareDomain() = assertFalse(Validators.wwwLink("google.com"))
    @Test fun wwwLinkHttp() = assertFalse(Validators.wwwLink("http://google.com"))
    @Test fun wwwLinkHttps() = assertFalse(Validators.wwwLink("https://google.com"))
    @Test fun wwwLinkValid() = assertTrue(Validators.wwwLink("www.google.com"))
    @Test fun wwwLinkWithQuery() = assertTrue(Validators.wwwLink("www.google.com/api/auth?name=Name&lastName=LastName"))

    // endregion

    // region httpLink

    @Test fun httpLinkNull() = assertFalse(Validators.httpLink(null))
    @Test fun httpLinkEmpty() = assertFalse(Validators.httpLink(""))
    @Test fun httpLinkBareDomain() = assertFalse(Validators.httpLink("google.com"))
    @Test fun httpLinkHttpDot() = assertFalse(Validators.httpLink("http.google.com"))
    @Test fun httpLinkWww() = assertFalse(Validators.httpLink("www.google.com"))
    @Test fun httpLinkHttps() = assertFalse(Validators.httpLink("https://google.com"))
    @Test fun httpLinkValid() = assertTrue(Validators.httpLink("http://google.com"))
    @Test fun httpLinkWithQuery() = assertTrue(Validators.httpLink("http://google.com/api/auth?name=Name&lastName=LastName"))

    // endregion

    // region httpsLink

    @Test fun httpsLinkNull() = assertFalse(Validators.httpsLink(null))
    @Test fun httpsLinkEmpty() = assertFalse(Validators.httpsLink(""))
    @Test fun httpsLinkBareDomain() = assertFalse(Validators.httpsLink("google.com"))
    @Test fun httpsLinkHttpsDot() = assertFalse(Validators.httpsLink("https.google.com"))
    @Test fun httpsLinkWww() = assertFalse(Validators.httpsLink("www.google.com"))
    @Test fun httpsLinkHttp() = assertFalse(Validators.httpsLink("http://google.com"))
    @Test fun httpsLinkValid() = assertTrue(Validators.httpsLink("https://google.com"))
    @Test fun httpsLinkWithQuery() = assertTrue(Validators.httpsLink("https://google.com/api/auth?name=Name&lastName=LastName"))

    // endregion

    // region ip

    @Test fun ipNull() = assertFalse(Validators.ip(null))
    @Test fun ipEmpty() = assertFalse(Validators.ip(""))
    @Test fun ipText() = assertFalse(Validators.ip("text"))
    @Test fun ipShortNumber() = assertFalse(Validators.ip("128"))
    @Test fun ipOctetOver255() = assertFalse(Validators.ip("10.0.0.256"))
    @Test fun ipFiveOctets() = assertFalse(Validators.ip("10.0.0.0.1"))
    @Test fun ipDoubleDoubleColon() = assertFalse(Validators.ip("ffff::ffff::ffff"))
    @Test fun ipTooManyHex() = assertFalse(Validators.ip("fffff::ffff"))
    @Test fun ipInvalidHex() = assertFalse(Validators.ip("fffg::ffff"))
    @Test fun ipIpv4_127() = assertTrue(Validators.ip("127.0.0.1"))
    @Test fun ipIpv4_192() = assertTrue(Validators.ip("192.168.0.109"))
    @Test fun ipIpv4_10() = assertTrue(Validators.ip("10.0.0.1"))
    @Test fun ipIpv6Full() = assertTrue(Validators.ip("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff"))
    @Test fun ipIpv6Compressed1() = assertTrue(Validators.ip("ffff::"))
    @Test fun ipIpv6Compressed2() = assertTrue(Validators.ip("ffff::ffff"))
    @Test fun ipIpv6Compressed3() = assertTrue(Validators.ip("ffff:ffff::ffff"))

    // endregion

    // region ipv4

    @Test fun ipv4Null() = assertFalse(Validators.ipv4(null))
    @Test fun ipv4Empty() = assertFalse(Validators.ipv4(""))
    @Test fun ipv4Text() = assertFalse(Validators.ipv4("text"))
    @Test fun ipv4OctetOver255() = assertFalse(Validators.ipv4("10.0.0.256"))
    @Test fun ipv4FiveOctets() = assertFalse(Validators.ipv4("10.0.0.0.1"))
    @Test fun ipv4RejectsIpv6_1() = assertFalse(Validators.ipv4("ffff::ffff"))
    @Test fun ipv4RejectsIpv6_2() = assertFalse(Validators.ipv4("ffff::"))
    @Test fun ipv4RejectsIpv6_3() = assertFalse(Validators.ipv4("ffff:ffff::ffff"))
    @Test fun ipv4RejectsIpv6Full() = assertFalse(Validators.ipv4("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff"))
    @Test fun ipv4Valid_127() = assertTrue(Validators.ipv4("127.0.0.1"))
    @Test fun ipv4Valid_192() = assertTrue(Validators.ipv4("192.168.0.109"))
    @Test fun ipv4Valid_10() = assertTrue(Validators.ipv4("10.0.0.1"))

    // endregion

    // region ipv6

    @Test fun ipv6Null() = assertFalse(Validators.ipv6(null))
    @Test fun ipv6Empty() = assertFalse(Validators.ipv6(""))
    @Test fun ipv6RejectsIpv4() = assertFalse(Validators.ipv6("127.0.0.1"))
    @Test fun ipv6DoubleColon() = assertFalse(Validators.ipv6("ffff::ffff::ffff"))
    @Test fun ipv6TooManyHex() = assertFalse(Validators.ipv6("fffff::ffff"))
    @Test fun ipv6InvalidHex() = assertFalse(Validators.ipv6("fffg::ffff"))
    @Test fun ipv6Full() = assertTrue(Validators.ipv6("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff"))
    @Test fun ipv6Compressed1() = assertTrue(Validators.ipv6("ffff::"))
    @Test fun ipv6Compressed2() = assertTrue(Validators.ipv6("ffff::ffff"))
    @Test fun ipv6Compressed3() = assertTrue(Validators.ipv6("ffff:ffff::ffff"))

    // endregion

    // region time

    @Test fun timeNull() = assertFalse(Validators.time(null))
    @Test fun timeEmpty() = assertFalse(Validators.time(""))
    @Test fun timeNoColon() = assertFalse(Validators.time("1200"))
    @Test fun timeDate() = assertFalse(Validators.time("01/01/2020"))
    @Test fun timeDash() = assertFalse(Validators.time("12-30"))
    @Test fun timeDot() = assertFalse(Validators.time("12.50"))
    @Test fun timeOutOfRange() = assertFalse(Validators.time("25:00"))
    @Test fun time24WithAmPm() = assertFalse(Validators.time("13:00 am"))
    @Test fun time24Midnight() = assertTrue(Validators.time("00:00"))
    @Test fun time24Noon() = assertTrue(Validators.time("12:30"))
    @Test fun time12WithAm() = assertTrue(Validators.time("12:59 am"))
    @Test fun time24EndTime() = assertTrue(Validators.time("23:59"))
    @Test fun time12Pm() = assertTrue(Validators.time("1:00 pm"))
    @Test fun time12AmUppercase() = assertTrue(Validators.time("01:00AM"))
    @Test fun time12PmLower() = assertTrue(Validators.time("01:00pm"))
    @Test fun time12PmMixed() = assertTrue(Validators.time("01:00PM"))

    // endregion

    // region time12

    @Test fun time12Null() = assertFalse(Validators.time12(null))
    @Test fun time12Empty() = assertFalse(Validators.time12(""))
    @Test fun time12NoColon() = assertFalse(Validators.time12("1200"))
    @Test fun time12OutOfRange() = assertFalse(Validators.time12("25:00"))
    @Test fun time12Hour13() = assertFalse(Validators.time12("13:00 am"))
    @Test fun time12Rejects24() = assertFalse(Validators.time12("23:59"))
    @Test fun time12Rejects00() = assertFalse(Validators.time12("00:00"))
    @Test fun time12ValidAm() = assertTrue(Validators.time12("12:59 am"))
    @Test fun time12ValidPm() = assertTrue(Validators.time12("1:00 pm"))
    @Test fun time12AmUpperCase() = assertTrue(Validators.time12("01:00AM"))
    @Test fun time12PmLowerCase() = assertTrue(Validators.time12("01:00pm"))

    // endregion

    // region time24

    @Test fun time24Null() = assertFalse(Validators.time24(null))
    @Test fun time24Empty() = assertFalse(Validators.time24(""))
    @Test fun time24Rejects12Am() = assertFalse(Validators.time24("12:59 am"))
    @Test fun time24Rejects1Pm() = assertFalse(Validators.time24("1:00 pm"))
    @Test fun time24RejectsAmUppercase() = assertFalse(Validators.time24("01:00AM"))
    @Test fun time24RejectsPmLower() = assertFalse(Validators.time24("01:00pm"))
    @Test fun time24OutOfRange() = assertFalse(Validators.time24("25:00"))
    @Test fun time24_13() = assertTrue(Validators.time24("13:00"))
    @Test fun time24_23_59() = assertTrue(Validators.time24("23:59"))
    @Test fun time24_00_00() = assertTrue(Validators.time24("00:00"))

    // endregion

    // region name

    @Test fun nameNull() = assertFalse(Validators.name(null))
    @Test fun nameEmpty() = assertFalse(Validators.name(""))
    @Test fun nameDigits() = assertFalse(Validators.name("10"))
    @Test fun nameStartsWithDigit() = assertFalse(Validators.name("1jose"))
    @Test fun nameWithAt() = assertFalse(Validators.name("@omar"))
    @Test fun nameLowercase() = assertTrue(Validators.name("jesus"))
    @Test fun nameCapitalized() = assertTrue(Validators.name("Jesus"))
    @Test fun nameUppercase() = assertTrue(Validators.name("JESUS"))
    @Test fun nameCompound() = assertTrue(Validators.name("jesus alberto"))
    @Test fun nameCompoundCapitalized() = assertTrue(Validators.name("Jesus Alberto"))
    @Test fun nameCompoundUppercase() = assertTrue(Validators.name("JESUS ALBERTO"))
    @Test fun nameWithDe() = assertTrue(Validators.name("jose de rosa"))

    // endregion

    // region onlyLetters

    @Test fun onlyLettersNull() = assertFalse(Validators.onlyLetters(null))
    @Test fun onlyLettersEmpty() = assertFalse(Validators.onlyLetters(""))
    @Test fun onlyLettersDigits() = assertFalse(Validators.onlyLetters("12"))
    @Test fun onlyLettersSpecial() = assertFalse(Validators.onlyLetters("*/"))
    @Test fun onlyLettersMixed() = assertFalse(Validators.onlyLetters("a1"))
    @Test fun onlyLettersWithDash() = assertFalse(Validators.onlyLetters("a-"))
    @Test fun onlyLettersAlphabet() = assertTrue(Validators.onlyLetters(Alphabets.ALPHABET))

    // endregion

    // region onlyAlphanumeric

    @Test fun onlyAlphanumericNull() = assertFalse(Validators.onlyAlphanumeric(null))
    @Test fun onlyAlphanumericEmpty() = assertFalse(Validators.onlyAlphanumeric(""))
    @Test fun onlyAlphanumericDash() = assertFalse(Validators.onlyAlphanumeric("-"))
    @Test fun onlyAlphanumericLetterSpecial() = assertFalse(Validators.onlyAlphanumeric("a*"))
    @Test fun onlyAlphanumericGreaterThan() = assertFalse(Validators.onlyAlphanumeric(">text"))
    @Test fun onlyAlphanumericWithDash() = assertFalse(Validators.onlyAlphanumeric("a-"))
    @Test fun onlyAlphanumericDecimal() = assertFalse(Validators.onlyAlphanumeric("-1.61"))
    @Test fun onlyAlphanumericCurrency() = assertFalse(Validators.onlyAlphanumeric("$10,320.00"))
    @Test fun onlyAlphanumericWithSpace() = assertFalse(Validators.onlyAlphanumeric("a b"))
    @Test fun onlyAlphanumericAlphaNumeric() = assertTrue(Validators.onlyAlphanumeric(Alphabets.ALPHA_NUMERIC))

    // endregion

    // region onlyNumbers

    @Test fun onlyNumbersNull() = assertFalse(Validators.onlyNumbers(null))
    @Test fun onlyNumbersEmpty() = assertFalse(Validators.onlyNumbers(""))
    @Test fun onlyNumbersText() = assertFalse(Validators.onlyNumbers("text"))
    @Test fun onlyNumbersName() = assertFalse(Validators.onlyNumbers("Name Lastname"))
    @Test fun onlyNumbersAlphaSuffix() = assertFalse(Validators.onlyNumbers("1a"))
    @Test fun onlyNumbersAlphaPrefix() = assertFalse(Validators.onlyNumbers("a1"))
    @Test fun onlyNumbersAlternating() = assertFalse(Validators.onlyNumbers("1a1"))
    @Test fun onlyNumbersDecimal() = assertFalse(Validators.onlyNumbers("1.00"))
    @Test fun onlyNumbersComma() = assertFalse(Validators.onlyNumbers("1,00"))
    @Test fun onlyNumbersValid() = assertTrue(Validators.onlyNumbers("123456789"))

    // endregion

    // region numberPattern

    @Test fun numberPatternNull() = assertFalse(Validators.numberPattern(null, "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternEmpty() = assertFalse(Validators.numberPattern("", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternText() = assertFalse(Validators.numberPattern("example", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternShort() = assertFalse(Validators.numberPattern("128", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternTrailingSpace() = assertFalse(Validators.numberPattern("+58 (412) 756-41-79 ", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternLeadingSpace() = assertFalse(Validators.numberPattern(" +58 (412) 756-41-79", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternAlphaInDigitPos() = assertFalse(Validators.numberPattern("+a8 (412) 756-41-79", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternValid() = assertTrue(Validators.numberPattern("+12 (345) 678-90-12", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternXsAsAlpha() = assertTrue(Validators.numberPattern("+xx (345) 678-90-12", "+xx (xxx) xxx-xx-xx"))
    @Test fun numberPatternAllXs() = assertTrue(Validators.numberPattern("+xx (xxx) xxx-xx-xx", "+xx (xxx) xxx-xx-xx"))

    // endregion

    // region shouldOnlyContain

    @Test fun shouldOnlyContainNull() = assertFalse(Validators.shouldOnlyContain(null, Alphabets.OCT))
    @Test fun shouldOnlyContainEmpty() = assertFalse(Validators.shouldOnlyContain("", Alphabets.OCT))
    @Test fun shouldOnlyContainText() = assertFalse(Validators.shouldOnlyContain("text", Alphabets.OCT))
    @Test fun shouldOnlyContainOutsideChar() = assertFalse(Validators.shouldOnlyContain("012345678", Alphabets.OCT))
    @Test fun shouldOnlyContainSpecial() = assertFalse(Validators.shouldOnlyContain("/*", Alphabets.OCT))
    @Test fun shouldOnlyContainValid() = assertTrue(Validators.shouldOnlyContain("01234567", Alphabets.OCT))
    @Test fun shouldOnlyContainSubset() = assertTrue(Validators.shouldOnlyContain("00", Alphabets.OCT))

    // endregion

    // region notContain

    @Test fun notContainNull() = assertFalse(Validators.notContain(null, Alphabets.OCT))
    @Test fun notContainEmpty() = assertFalse(Validators.notContain("", Alphabets.OCT))
    @Test fun notContainCharInAlphabet() = assertFalse(Validators.notContain("0", Alphabets.OCT))
    @Test fun notContainMixedChar() = assertFalse(Validators.notContain("text4", Alphabets.OCT))
    @Test fun notContainOutsideChars() = assertTrue(Validators.notContain("89", Alphabets.OCT))
    @Test fun notContainTextOnly() = assertTrue(Validators.notContain("text", Alphabets.OCT))
    @Test fun notContainMixedOutside() = assertTrue(Validators.notContain("@nic89", Alphabets.OCT))

    // endregion

    // region mustContainOne

    @Test fun mustContainOneNull() = assertFalse(Validators.mustContainOne(null, Alphabets.OCT))
    @Test fun mustContainOneEmpty() = assertFalse(Validators.mustContainOne("", Alphabets.OCT))
    @Test fun mustContainOneNoMatch() = assertFalse(Validators.mustContainOne("text", Alphabets.OCT))
    @Test fun mustContainOneAtNoMatch() = assertFalse(Validators.mustContainOne("@nick", Alphabets.OCT))
    @Test fun mustContainOneOutsideDigits() = assertFalse(Validators.mustContainOne("@nick89", Alphabets.OCT))
    @Test fun mustContainOneSingleChar() = assertTrue(Validators.mustContainOne("0", Alphabets.OCT))
    @Test fun mustContainOneMixed() = assertTrue(Validators.mustContainOne("@nick1", Alphabets.OCT))
    @Test fun mustContainOneDigitInMixed() = assertTrue(Validators.mustContainOne("91", Alphabets.OCT))

    // endregion

    // region mustContainMin

    @Test fun mustContainMinNull() = assertFalse(Validators.mustContainMin(null, 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinEmpty() = assertFalse(Validators.mustContainMin("", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinAllUppercase() = assertFalse(Validators.mustContainMin("ABC", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinDigits() = assertFalse(Validators.mustContainMin("123", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinBelowMin() = assertFalse(Validators.mustContainMin("abC", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinExact() = assertTrue(Validators.mustContainMin("abc", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinAbove() = assertTrue(Validators.mustContainMin("abcd", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinMixed() = assertTrue(Validators.mustContainMin("aBcDe", 3, Alphabets.ALPHA_LOWERCASE))
    @Test fun mustContainMinComplex() = assertTrue(Validators.mustContainMin("abcABC123...", 3, Alphabets.ALPHA_LOWERCASE))

    // endregion

    // region maxValue

    @Test fun maxValueNull() = assertFalse(Validators.maxValue(null, 2.5))
    @Test fun maxValueEmpty() = assertFalse(Validators.maxValue("", 2.5))
    @Test fun maxValueText() = assertFalse(Validators.maxValue("text", 2.5))
    @Test fun maxValueAboveMax() = assertFalse(Validators.maxValue("2.51", 2.5))
    @Test fun maxValueFarAbove() = assertFalse(Validators.maxValue("30", 2.5))
    @Test fun maxValueHigh() = assertFalse(Validators.maxValue("91", 2.5))
    @Test fun maxValueComma() = assertFalse(Validators.maxValue("1,2", 2.5))
    @Test fun maxValueSlightlyAbove() = assertFalse(Validators.maxValue("2.6", 2.5))
    @Test fun maxValueExact() = assertTrue(Validators.maxValue("2.5", 2.5))
    @Test fun maxValueZero() = assertTrue(Validators.maxValue("0.0", 2.5))
    @Test fun maxValueNegative() = assertTrue(Validators.maxValue("-30", 2.5))
    @Test fun maxValueJustBelow() = assertTrue(Validators.maxValue("2.49", 2.5))

    // endregion

    // region minValue

    @Test fun minValueNull() = assertFalse(Validators.minValue(null, 2.5))
    @Test fun minValueEmpty() = assertFalse(Validators.minValue("", 2.5))
    @Test fun minValueText() = assertFalse(Validators.minValue("text", 2.5))
    @Test fun minValueComma() = assertFalse(Validators.minValue("2,5", 2.5))
    @Test fun minValueJustBelow() = assertFalse(Validators.minValue("2.49", 2.5))
    @Test fun minValueZero() = assertFalse(Validators.minValue("0", 2.5))
    @Test fun minValueNegative() = assertFalse(Validators.minValue("-2.5", 2.5))
    @Test fun minValueExact() = assertTrue(Validators.minValue("2.5", 2.5))
    @Test fun minValueJustAbove() = assertTrue(Validators.minValue("2.51", 2.5))
    @Test fun minValueFarAbove() = assertTrue(Validators.minValue("30", 2.5))

    // endregion

    // region rangeValue

    @Test fun rangeValueNull() = assertFalse(Validators.rangeValue(null, 10.0, 30.0))
    @Test fun rangeValueEmpty() = assertFalse(Validators.rangeValue("", 10.0, 30.0))
    @Test fun rangeValueBelowMin() = assertFalse(Validators.rangeValue("9", 10.0, 30.0))
    @Test fun rangeValueAboveMax() = assertFalse(Validators.rangeValue("31", 10.0, 30.0))
    @Test fun rangeValueAtMin() = assertTrue(Validators.rangeValue("10", 10.0, 30.0))
    @Test fun rangeValueAtMax() = assertTrue(Validators.rangeValue("30", 10.0, 30.0))
    @Test fun rangeValueInRange() = assertTrue(Validators.rangeValue("20", 10.0, 30.0))

    // endregion
}