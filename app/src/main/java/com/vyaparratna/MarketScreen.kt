package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(navController: NavHostController) {
    var selectedMarket by remember { mutableStateOf("Gold") }
    var selectedVaar by remember { mutableStateOf("रविवार") }
    var resultText by remember { mutableStateOf("") }
    var isTeji by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("बाजार विश्लेषण") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "📊 बाजार चुनें",
                style = MaterialTheme.typography.titleLarge
            )

            // 4 Market Buttons
            MarketData.marketDhruvank.keys.forEach { market ->
                Button(
                    onClick = { selectedMarket = market },
                    modifier = Modifier.fillMaxWidth(),
                    colors = if (selectedMarket == market)
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(
                        text = if (selectedMarket == market) "✅ $market" else market,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "📅 वार चुनें",
                style = MaterialTheme.typography.titleLarge
            )

            // Vaar Buttons
            DhruvankData.vaarDhruvank.keys.forEach { vaar ->
                Button(
                    onClick = { selectedVaar = vaar },
                    modifier = Modifier.fillMaxWidth(),
                    colors = if (selectedVaar == vaar)
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(
                        text = if (selectedVaar == vaar) "✅ $vaar" else vaar
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calculate Button
            Button(
                onClick = {
                    try {
                        val marketVal = MarketData.marketDhruvank[selectedMarket] ?: 0
                        val vaarVal = DhruvankData.vaarDhruvank[selectedVaar] ?: 0
                        val total = marketVal + vaarVal
                        val remainder = total % 8

                        val prediction: String
                        if (remainder % 2 == 0) {
                            isTeji = true
                            prediction = "📈 तेजी (Teji)"
                        } else {
                            isTeji = false
                            prediction = "📉 मंदी (Mandi)"
                        }

                        resultText = "📊 विश्लेषण:\n\n" +
                                "बाजार: $selectedMarket\n" +
                                "ध्रुवांक: $marketVal\n\n" +
                                "वार: $selectedVaar\n" +
                                "ध्रुवांक: $vaarVal\n\n" +
                                "कुल योग: $total\n" +
                                "शेष (÷8): $remainder\n\n" +
                                "अनुमान: $prediction"

                        showResult = true
                    } catch (e: Throwable) {
                        resultText = "त्रुटि: ${e.message}"
                        showResult = true
                        isTeji = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "गणना करें (Calculate)",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Result Card
            if (showResult) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (isTeji) "📈 तेजी" else "📉 मंदी",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Back Button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("← वापस जाओ")
            }
        }
    }
}
