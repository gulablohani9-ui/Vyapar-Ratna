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
fun AnalysisScreen(navController: NavHostController) {
    var selectedCommodity by remember { mutableStateOf("गेहूँ") }
    var selectedVaar by remember { mutableStateOf("रविवार") }
    var resultText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("तेजी-मंदी विश्लेषण") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("← वापस", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "📊 तेजी-मंदी विश्लेषण",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                "नमस्ते! यह स्क्रीन काम कर रही है।",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                "अभी select किया हुआ: वस्तु = $selectedCommodity, वार = $selectedVaar",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            Text("वस्तु चुनें:", style = MaterialTheme.typography.titleMedium)

            // Simple buttons instead of dropdown
            val commodities = listOf("गेहूँ", "जौ", "चना", "सोना", "चाँदी", "रुई")
            commodities.forEach { item ->
                Button(
                    onClick = { selectedCommodity = item },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(item)
                }
            }

            Divider()

            Text("वार चुनें:", style = MaterialTheme.typography.titleMedium)

            val vaars = listOf("रविवार", "सोमवार", "मंगलवार", "बुधवार", "गुरुवार", "शुक्रवार", "शनिवार")
            vaars.forEach { item ->
                Button(
                    onClick = { selectedVaar = item },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(item)
                }
            }

            Divider()

            Button(
                onClick = {
                    val commodityVal = DhruvankData.commodityDhruvank[selectedCommodity] ?: 0
                    val vaarVal = DhruvankData.vaarDhruvank[selectedVaar] ?: 0
                    val total = commodityVal + vaarVal
                    val remainder = total % 8
                    val prediction = if (remainder % 2 == 0) "📈 तेजी" else "📉 मंदी"
                    resultText = "वस्तु: $selectedCommodity ($commodityVal)\n" +
                            "वार: $selectedVaar ($vaarVal)\n" +
                            "कुल योग: $total\n" +
                            "शेष: $remainder\n\n" +
                            "अनुमान: $prediction"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("गणना करें")
            }

            if (resultText.isNotEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
