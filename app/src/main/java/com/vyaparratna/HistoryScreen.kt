package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController) {
    val context = LocalContext.current
    var predictions by remember { mutableStateOf(PredictionStore.loadAll(context)) }
    var showClearDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("पूर्वानुमान इतिहास") },
                actions = {
                    if (predictions.isNotEmpty()) {
                        TextButton(onClick = { showClearDialog = true }) {
                            Text("साफ करें")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            if (predictions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            "📭 कोई इतिहास नहीं",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "जब भी आप गणना करेंगे, यहाँ दिखेगा।",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { navController.popBackStack() }) {
                            Text("← वापस जाओ")
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(predictions) { entry ->
                        val isTeji = entry.result.contains("तेजी", ignoreCase = true)

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isTeji)
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        entry.market,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        if (isTeji) "📈 तेजी" else "📉 मंदी",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    "${entry.nakshatra} • ${entry.tithi} • ${entry.vaar}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    "${entry.rashi} • ${entry.city}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    "मूल्य: ${entry.price}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    "अनुमान: ${entry.predicted}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "🕐 ${entry.timestamp}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    item {
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
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("इतिहास साफ करें?") },
            text = { Text("सारे पूर्वानुमान हट जाएंगे। यह वापस नहीं हो सकता।") },
            confirmButton = {
                TextButton(
                    onClick = {
                        PredictionStore.clear(context)
                        predictions = emptyList()
                        showClearDialog = false
                    }
                ) {
                    Text("हाँ, साफ करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("रद्द करें")
                }
            }
        )
    }
}
