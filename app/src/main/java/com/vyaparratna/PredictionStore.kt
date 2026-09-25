package com.vyaparratna

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PredictionStore {
    private const val PREFS_NAME = "vyapar_prefs"
    private const val KEY_PREDICTIONS = "predictions"
    private const val SEPARATOR = "|||"

    fun save(
        context: Context,
        market: String,
        nakshatra: String,
        tithi: String,
        vaar: String,
        rashi: String,
        city: String,
        price: String,
        predicted: String,
        result: String
    ) {
        val timestamp = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date())

        val entry = listOf(
            timestamp,
            market,
            nakshatra,
            tithi,
            vaar,
            rashi,
            city,
            price,
            predicted,
            result
        ).joinToString("~")

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_PREDICTIONS, "") ?: ""
        val updated = if (existing.isEmpty()) entry else "$entry$SEPARATOR$existing"
        prefs.edit().putString(KEY_PREDICTIONS, updated).apply()
    }

    fun loadAll(context: Context): List<PredictionEntry> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_PREDICTIONS, "") ?: ""
        if (raw.isEmpty()) return emptyList()

        return raw.split(SEPARATOR).mapNotNull { line ->
            val parts = line.split("~")
            if (parts.size >= 10) {
                PredictionEntry(
                    timestamp = parts[0],
                    market = parts[1],
                    nakshatra = parts[2],
                    tithi = parts[3],
                    vaar = parts[4],
                    rashi = parts[5],
                    city = parts[6],
                    price = parts[7],
                    predicted = parts[8],
                    result = parts[9]
                )
            } else null
        }
    }

    fun clear(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_PREDICTIONS).apply()
    }
}

data class PredictionEntry(
    val timestamp: String,
    val market: String,
    val nakshatra: String,
    val tithi: String,
    val vaar: String,
    val rashi: String,
    val city: String,
    val price: String,
    val predicted: String,
    val result: String
)
