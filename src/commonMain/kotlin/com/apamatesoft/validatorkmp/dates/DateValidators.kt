package com.apamatesoft.validatorkmp.dates

/**
 * Validates that [evaluate] matches the specified date [format].
 *
 * @param evaluate the date string to validate.
 * @param format the expected date format.
 * @return `true` if the string is a valid date in the given format, `false` otherwise.
 */
expect fun validateDate(evaluate: String, format: DateFormats): Boolean

/**
 * Validates that the age calculated from [evaluate] (parsed with [format]) is at least [age] years.
 *
 * **Warning:** This function uses the device's current date.
 *
 * @param evaluate the date string to validate.
 * @param format the expected date format.
 * @param age the minimum age in years.
 * @return `true` if the person born on the given date is at least [age] years old, `false` otherwise.
 */
expect fun validateMinAge(evaluate: String, format: DateFormats, age: Int): Boolean

/**
 * Validates that the date represented by [evaluate] (parsed with [format]) has not expired.
 *
 * **Warning:** This function uses the device's current date.
 *
 * @param evaluate the date string to validate.
 * @param format the expected date format.
 * @return `true` if the date has not expired (is in the future or today), `false` otherwise.
 */
expect fun validateExpirationDate(evaluate: String, format: DateFormats): Boolean