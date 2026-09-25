package com.vyaparratna

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

object PanchangHelper {

    private val nakshatraList = listOf(
        "अश्विनी", "भरणी", "कृत्तिका", "रोहिणी", "मृगशिरा",
        "आर्द्रा", "पुनर्वसु", "पुष्य", "अश्लेषा", "मघा",
        "पूर्वाफाल्गुनी", "उत्तराफाल्गुनी", "हस्त", "चित्रा",
        "स्वाति", "विशाखा", "अनुराधा", "ज्येष्ठा", "मूल",
        "पूर्वाषाढा", "उत्तराषाढा", "श्रवण", "धनिष्ठा",
        "शतभिषा", "पूर्वाभाद्रपद", "उत्तराभाद्रपद", "रेवती"
    )

    private val tithiList = listOf(
        "प्रतिपदा", "द्वितीया", "तृतीया", "चतुर्थी", "पंचमी",
        "षष्ठी", "सप्तमी", "अष्टमी", "नवमी", "दशमी",
        "एकादशी", "द्वादशी", "त्रयोदशी", "चतुर्दशी"
    )

    // Date se Vaar
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

    // Approximate Tithi (based on lunar month cycle)
    fun getApproxTithi(date: Date): String {
        // Reference new moon: 11 January 2024
        val refNewMoon = GregorianCalendar(2024, 0, 11, 11, 57).timeInMillis
        val daysSince = (date.time - refNewMoon) / (1000.0 * 60 * 60 * 24)
        val lunarMonth = 29.530588853
        val daysInCycle = ((daysSince % lunarMonth) + lunarMonth) % lunarMonth
        val tithiNum = (daysInCycle / (lunarMonth / 30.0)).toInt() + 1

        return if (tithiNum <= 15) {
            if (tithiNum == 15) "पूर्णिमा" else tithiList[tithiNum - 1]
        } else {
            val krishnaNum = tithiNum - 15
            if (krishnaNum == 15) "अमावास्या" else tithiList[krishnaNum - 1]
        }
    }

    // Approximate Nakshatra (based on sidereal month cycle)
    fun getApproxNakshatra(date: Date): String {
        // Reference: 1 January 2024, Moon in approximate position
        val refDate = GregorianCalendar(2024, 0, 1).timeInMillis
        val daysSince = (date.time - refDate) / (1000.0 * 60 * 60 * 24)
        val nakshatraLength = 27.321661 / 27.0 // ~1.012 days per nakshatra
        val idx = (((daysSince / nakshatraLength).toInt() % 27) + 27) % 27
        return nakshatraList[idx]
    }

    // Date formatting
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

    // Aaj, kal, parson
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

    fun formatDateShort(date: Date): String {
        return SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(date)
    }
}
