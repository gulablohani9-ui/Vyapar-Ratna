package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitScreen(navController: NavHostController) {
    val today = Date()
    val cal = Calendar.getInstance()
    cal.time = today

    // Simplified transit - approximate positions
    val planets = listOf(
        "☀️ सूर्य" to rashiFromIndex((cal.get(Calendar.MONTH) + 1) % 12),
        "🌙 चन्द्र" to rashiFromIndex((cal.get(Calendar.DAY_OF_YEAR) / 2) % 12),
        "♂️ मंगल" to rashiFromIndex((cal.get(Calendar.DAY_OF_YEAR) / 45) % 12),
        "☿️ बुध" to rashiFromIndex((cal.get(Calendar.DAY_OF_YEAR) / 25) % 12),
        "♃ गुरु" to rashiFromIndex((cal.get(Calendar.YEAR) % 12)),
        "♀️ शुक्र" to rashiFromIndex((cal.get(Calendar.DAY_OF_YEAR) / 30) % 12),
        "♄ शनि" to rashiFromIndex(((cal.get(Calendar.YEAR) - 2024) / 2) % 12),
        "☊ राहु" to rashiFromIndex((cal.get(Calendar.YEAR) + 6) % 12),
        "☋ केतु" to rashiFromIndex((cal.get(Calendar.YEAR) + 6 + 6) % 12)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ग्रह गोचर (Transit)") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "📅 ${PanchangHelper.formatDateHindi(today)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "वार: ${PanchangHelper.getVaarFromDate(today)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "नक्षत्र: ${PanchangHelper.getApproxNakshatra(today)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "तिथि: ${PanchangHelper.getApproxTithi(today)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "🪐 आज के ग्रह गोचर (Approximate)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            planets.forEach { (planet, rashi) ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(planet, style = MaterialTheme.typography.bodyLarge)
                        Text(rashi, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Text(
                "ℹ️ ये approximate positions हैं। Exact positions के लिए Swiss Ephemeris जोड़ेंगे।",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("← वापस जाओ")
            }
        }
    }
}

fun rashiFromIndex(index: Int): String {
    val rashis = listOf(
        "मेष", "वृष", "मिथुन", "कर्क", "सिंह", "कन्या",
        "तुला", "वृश्चिक", "धनु", "मकर", "कुम्भ", "मीन"
    )
    val i = ((index % 12) + 12) % 12
    return rashis[i]
}
