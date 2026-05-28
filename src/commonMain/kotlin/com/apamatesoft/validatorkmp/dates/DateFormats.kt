package com.apamatesoft.validatorkmp.dates

/**
 * Enum representing supported date formats for cross-platform date validation.
 *
 * Use these enum values with [Validator][com.apamatesoft.validatorkmp.Validator] date rules
 * (`date`, `minAge`, `expirationDate`) and with [validateDate], [validateMinAge],
 * and [validateExpirationDate] functions.
 *
 * The platform-specific pattern string is obtained via [pattern].
 */
enum class DateFormats {
    /** Format: `yyyy-MM-dd` (e.g. 2024-01-15) */
    YYYY_MM_DD,
    /** Format: `dd/MM/yyyy` (e.g. 15/01/2024) */
    DD_MM_YYYY,
    /** Format: `MM/dd/yyyy` (e.g. 01/15/2024) */
    MM_DD_YYYY,
    /** Format: `yyyy-MM-dd HH:mm` (e.g. 2024-01-15 14:30) */
    YYYY_MM_DD_HH_MM,
    /** Format: `dd/MM/yyyy HH:mm` (e.g. 15/01/2024 14:30) */
    DD_MM_YYYY_HH_MM,
    /** Format: `HH:mm` (e.g. 14:30) */
    HH_MM,
    /** Format: `HH:mm:ss` (e.g. 14:30:45) */
    HH_MM_SS
}

/**
 * Returns the platform-specific date format pattern string for this enum value.
 *
 * On Android, returns Java `SimpleDateFormat` patterns.
 * On iOS, returns equivalent Unicode date format patterns.
 */
expect fun DateFormats.pattern(): String