package com.vyaparratna

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object PanchangHelper {

    // Date se Vaar nikalo (100% accurate)
    fun getVaarFromDate(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "रविवार"
            Calendar.MONDAY -> "सोमवार"
            Calendar.TUESDAY -> "मंगलवार"
            Calendar.WEDNESDAY -> "बुधवार"
            Calendar.THURSDAY -> "गुरुवार"
            Calendar.FRIDAY -> "शुक्रवार"
            Calendar.SATURDAY -> "शनिवार"
            else -> "रविवार"
        }
    }

    // Date ko format karo (Hindi mein)
    fun formatDateHindi(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val year = cal.get(Calendar.YEAR)
        val month = when (cal.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "जनवरी"
            Calendar.FEBRUARY -> "फरवरी"
            Calendar.MARCH -> "मार्च"
            Calendar.APRIL -> "अप्रैल"
            Calendar.MAY -> "मई"
            Calendar.JUNE -> "जून"
            Calendar.JULY -> "जुलाई"
            Calendar.AUGUST -> "अगस्त"
            Calendar.SEPTEMBER -> "सितंबर"
            Calendar.OCTOBER -> "अक्टूबर"
            Calendar.NOVEMBER -> "नवंबर"
            Calendar.DECEMBER -> "दिसंबर"
            else -> ""
        }
        return "$day $month $year"
    }

    // Aaj, kal, parson ki date
    fun today(): Date = Date()

    fun tomorrow(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 1)
        return cal.time
    }

    fun dayAfterTomorrow(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 2)
        return cal.time
    }

    // Custom date
    fun customDate(year: Int, month: Int, day: Int): Date {
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        return cal.time
    }

    fun formatDateShort(date: Date): String {
        return SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(date)
    }
}
