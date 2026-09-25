package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(navController: NavHostController) {
    var selectedMarket by remember { mutableStateOf("Gold") }
    var selectedVaar by remember { mutableStateOf("रविवार") }
    var priceInput by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var predictedPrice by remember { mutableStateOf("") }
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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "💰 आज का भाव (Price)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = priceInput,
                onValueChange = { priceInput = it },
                label = { Text("जैसे: 75000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    try {
                        val marketVal = MarketData.marketDhruvank[selectedMarket] ?: 0
                        val vaarVal = DhruvankData.vaarDhruvank[selectedVaar] ?: 0
                        val total = marketVal + vaarVal
                        val remainder = total % 8

                        // Teji ya Mandi
                        isTeji = remainder % 2 == 0

                        // Price calculation
                        val price = priceInput.toDoubleOrNull() ?: 0.0
                        val changePercent = when {
                            isTeji -> 1.0 + (remainder * 0.5)  // 0.5% to 4%
                            else -> 1.0 - (remainder * 0.5)    // -0.5% to -4%
                        }
                        val predicted = price * changePercent
                        val change = predicted - price

                        predictedPrice = if (price > 0) {
                            "अनुमानित भाव: ₹%.2f\n".format(predicted) +
                            "बदलाव: ₹%.2f (%.2f%%)".format(change, (change / price) * 100)
                        } else {
                            "कृपया पहले मूल्य डालें"
                        }

                        resultText = "📊 विश्लेषण:\n\n" +
                                "बाजार: $selectedMarket\n" +
                                "ध्रुवांक: $marketVal\n\n" +
                                "वार: $selectedVaar\n" +
                                "ध्रुवांक: $vaarVal\n\n" +
                                "कुल योग: $total\n" +
                                "शेष (÷8): $remainder\n"

                        showResult = true
                    } catch (e: Throwable) {
                        resultText = "त्रुटि: ${e.message}"
                        showResult = true
                        isTeji = false
                        predictedPrice = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "गणना करें (Calculate)",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (showResult) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isTeji)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (isTeji) "📈 तेजी (Teji)" else "📉 मंदी (Mandi)",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (predictedPrice.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = predictedPrice,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("← वापस जाओ")
            }
        }
    }
}
