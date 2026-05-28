package com.apamatesoft.validatorkmp.dates

import kotlin.test.Test
import kotlin.test.assertEquals

class DateFormatsTest {

    @Test
    fun hasSevenEntries() {
        assertEquals(7, DateFormats.entries.size)
    }

    @Test
    fun yyyyMmDd() = assertEquals("YYYY_MM_DD", DateFormats.YYYY_MM_DD.name)

    @Test
    fun ddMmYyyy() = assertEquals("DD_MM_YYYY", DateFormats.DD_MM_YYYY.name)

    @Test
    fun mmDdYyyy() = assertEquals("MM_DD_YYYY", DateFormats.MM_DD_YYYY.name)

    @Test
    fun yyyyMmDdHhMm() = assertEquals("YYYY_MM_DD_HH_MM", DateFormats.YYYY_MM_DD_HH_MM.name)

    @Test
    fun ddMmYyyyHhMm() = assertEquals("DD_MM_YYYY_HH_MM", DateFormats.DD_MM_YYYY_HH_MM.name)

    @Test
    fun hhMm() = assertEquals("HH_MM", DateFormats.HH_MM.name)

    @Test
    fun hhMmSs() = assertEquals("HH_MM_SS", DateFormats.HH_MM_SS.name)
}