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
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanchangScreen(navController: NavHostController) {
    val today = Date()

    val vaar = PanchangHelper.getVaarFromDate(today)
    val nakshatra = PanchangHelper.getApproxNakshatra(today)
    val tithi = PanchangHelper.getApproxTithi(today)

    // Rahu Kaal (approximate based on vaar)
    val rahuKaal = when (vaar) {
        "रविवार" -> "16:30 - 18:00"
        "सोमवार" -> "07:30 - 09:00"
        "मंगलवार" -> "15:00 - 16:30"
        "बुधवार" -> "12:00 - 13:30"
        "गुरुवार" -> "13:30 - 15:00"
        "शुक्रवार" -> "10:30 - 12:00"
        "शनिवार" -> "09:00 - 10:30"
        else -> "—"
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("पंचांग") })
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
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "📅 ${PanchangHelper.formatDateHindi(today)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "🕉️ आज का पंचांग",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            PanchangRow("वार", vaar)
            PanchangRow("नक्षत्र", nakshatra)
            PanchangRow("तिथि", tithi)
            PanchangRow("राहु काल", rahuKaal)

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "⚠️ राहु काल सूचना",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "राहु काल में शुभ कार्य न करें। व्यापार में सावधानी रखें।",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

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

@Composable
fun PanchangRow(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
