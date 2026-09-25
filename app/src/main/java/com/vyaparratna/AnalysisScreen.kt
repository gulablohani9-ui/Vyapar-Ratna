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
    // Sirf 2 variables - jo hum jaante hain exist karte hain
    var selectedCommodity by remember { mutableStateOf("गेहूँ") }
    var resultText by remember { mutableStateOf("") }

    // Test: pehle check karo ki DhruvankData load ho raha hai ya nahi
    val commodityKeys: List<String> = try {
        DhruvankData.commodityDhruvank.keys.toList()
    } catch (e: Throwable) {
        listOf("ERROR: ${e.message}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("तेजी-मंदी विश्लेषण") }
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
                "✅ Analysis Screen काम कर रही है!",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "कुल वस्तुएँ: ${commodityKeys.size}",
                style = MaterialTheme.typography.bodyLarge
            )

            Divider()

            Text(
                "चुना हुआ: $selectedCommodity",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 6 buttons - jo hum jaante hain exist karte hain
            listOf("गेहूँ", "जौ", "चना", "सोना", "चाँदी", "रुई").forEach { item ->
                Button(
                    onClick = { selectedCommodity = item },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(item)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    try {
                        val v = DhruvankData.commodityDhruvank[selectedCommodity] ?: 0
                        val r = v % 8
                        val prediction = if (r % 2 == 0) "तेजी 📈" else "मंदी 📉"
                        resultText = "वस्तु: $selectedCommodity\nध्रुवांक: $v\nशेष: $r\n\nअनुमान: $prediction"
                    } catch (e: Throwable) {
                        resultText = "Error: ${e.message}"
                    }
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
