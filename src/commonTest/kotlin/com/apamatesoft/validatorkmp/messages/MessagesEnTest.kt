package com.apamatesoft.validatorkmp.messages

import kotlin.test.Test
import kotlin.test.assertEquals

class MessagesEnTest {

    private val messages = MessagesEn()

    @Test fun compareMessage() = assertEquals("Not match", messages.compareMessage)
    @Test fun dateMessage() = assertEquals("The date does not match the format %s", messages.dateMessage)
    @Test fun emailMessage() = assertEquals("Email invalid", messages.emailMessage)
    @Test fun expirationDateMessage() = assertEquals("Expired date", messages.expirationDateMessage)
    @Test fun httpLinkMessage() = assertEquals("Invalid http link", messages.httpLinkMessage)
    @Test fun httpsLinkMessage() = assertEquals("Invalid https link", messages.httpsLinkMessage)
    @Test fun ipMessage() = assertEquals("Invalid IP", messages.ipMessage)
    @Test fun ipv4Message() = assertEquals("Invalid IPv4", messages.ipv4Message)
    @Test fun ipv6Message() = assertEquals("Invalid IPv6", messages.ipv6Message)
    @Test fun lengthMessage() = assertEquals("It requires %d characters", messages.lengthMessage)
    @Test fun linkMessage() = assertEquals("Invalid link", messages.linkMessage)
    @Test fun maxLengthMessage() = assertEquals("%d or less characters required", messages.maxLengthMessage)
    @Test fun maxValueMessage() = assertEquals("The value cannot be greater than %1\$.2f", messages.maxValueMessage)
    @Test fun minAgeMessage() = assertEquals("You must be at least %d years old", messages.minAgeMessage)
    @Test fun minLengthMessage() = assertEquals("%d or more characters are required", messages.minLengthMessage)
    @Test fun minValueMessage() = assertEquals("The value cannot be less than %1\$.2f", messages.minValueMessage)
    @Test fun mustContainMinMessage() = assertEquals("At least %d of the following characters are required: %s", messages.mustContainMinMessage)
    @Test fun mustContainOneMessage() = assertEquals("At least one of the following characters is required: %s", messages.mustContainOneMessage)
    @Test fun nameMessage() = assertEquals("Invalid personal name", messages.nameMessage)
    @Test fun notContainMessage() = assertEquals("The following characters aren't admitted %s", messages.notContainMessage)
    @Test fun numberMessage() = assertEquals("It is not a number", messages.numberMessage)
    @Test fun numberPatternMessage() = assertEquals("Does not match pattern %s", messages.numberPatternMessage)
    @Test fun onlyAlphanumericMessage() = assertEquals("Just alphanumeric characters", messages.onlyAlphanumericMessage)
    @Test fun onlyLettersMessage() = assertEquals("Only letters", messages.onlyLettersMessage)
    @Test fun onlyNumbersMessage() = assertEquals("Only numbers", messages.onlyNumbersMessage)
    @Test fun rangeLengthMessage() = assertEquals("The text must contain between %d to %d characters", messages.rangeLengthMessage)
    @Test fun rangeValueMessage() = assertEquals("The value must be between %1\$.2f and %2\$.2f", messages.rangeValueMessage)
    @Test fun regExpMessage() = assertEquals("The value does not match the regular expression %s", messages.regExpMessage)
    @Test fun requiredMessage() = assertEquals("Required", messages.requiredMessage)
    @Test fun shouldOnlyContainMessage() = assertEquals("They are just admitted the following characters %s", messages.shouldOnlyContainMessage)
    @Test fun timeMessage() = assertEquals("Time invalid", messages.timeMessage)
    @Test fun time12Message() = assertEquals("Invalid 12 hour format", messages.time12Message)
    @Test fun time24Message() = assertEquals("Invalid 24 hour format", messages.time24Message)
    @Test fun wwwLinkMessage() = assertEquals("Invalid www link", messages.wwwLinkMessage)
}