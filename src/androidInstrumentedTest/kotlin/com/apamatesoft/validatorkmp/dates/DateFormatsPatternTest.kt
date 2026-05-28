package com.apamatesoft.validatorkmp.dates

import kotlin.test.Test
import kotlin.test.assertEquals

class DateFormatsPatternTest {

    @Test
    fun yyyyMmDd() = assertEquals("yyyy-MM-dd", DateFormats.YYYY_MM_DD.pattern())

    @Test
    fun ddMmYyyy() = assertEquals("dd/MM/yyyy", DateFormats.DD_MM_YYYY.pattern())

    @Test
    fun mmDdYyyy() = assertEquals("MM/dd/yyyy", DateFormats.MM_DD_YYYY.pattern())

    @Test
    fun yyyyMmDdHhMm() = assertEquals("yyyy-MM-dd HH:mm", DateFormats.YYYY_MM_DD_HH_MM.pattern())

    @Test
    fun ddMmYyyyHhMm() = assertEquals("dd/MM/yyyy HH:mm", DateFormats.DD_MM_YYYY_HH_MM.pattern())

    @Test
    fun hhMm() = assertEquals("HH:mm", DateFormats.HH_MM.pattern())

    @Test
    fun hhMmSs() = assertEquals("HH:mm:ss", DateFormats.HH_MM_SS.pattern())
}