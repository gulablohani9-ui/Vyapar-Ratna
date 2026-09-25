package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(navController: NavHostController) {
    var selectedCommodity by remember { mutableStateOf("गेहूँ") }
    var selectedNakshatra by remember { mutableStateOf("अश्विनी") }
    var selectedTithi by remember { mutableStateOf("प्रतिपदा") }
    var selectedVaar by remember { mutableStateOf("रविवार") }
    var selectedRashi by remember { mutableStateOf("मेष") }
    var selectedCity by remember { mutableStateOf("दिल्ली") }
    var resultText by remember { mutableStateOf("") }
    var isTeji by remember { mutableStateOf(false) }
    var hasResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("तेजी-मंदी विश्लेषण") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("विवरण भरें", style = MaterialTheme.typography.titleLarge)

            SimpleDropdown(
                label = "वस्तु चुनें",
                items = DhruvankData.commodityDhruvank.keys.toList(),
                onSelect = { selectedCommodity = it }
            )

            SimpleDropdown(
                label = "नक्षत्र चुनें",
                items = DhruvankData.nakshatraDhruvank.keys.toList(),
                onSelect = { selectedNakshatra = it }
            )

            SimpleDropdown(
                label = "तिथि चुनें",
                items = DhruvankData.tithiDhruvank.keys.toList(),
                onSelect = { selectedTithi = it }
            )

            SimpleDropdown(
                label = "वार चुनें",
                items = DhruvankData.vaarDhruvank.keys.toList(),
                onSelect = { selectedVaar = it }
            )

            SimpleDropdown(
                label = "राशि चुनें",
                items = DhruvankData.rashiDhruvank.keys.toList(),
                onSelect = { selectedRashi = it }
            )

            SimpleDropdown(
                label = "शहर चुनें",
                items = DhruvankData.cityDhruvank.keys.toList(),
                onSelect = { selectedCity = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    try {
                        val commodityVal = DhruvankData.commodityDhruvank[selectedCommodity] ?: 0
                        val nakshatraVal = DhruvankData.nakshatraDhruvank[selectedNakshatra] ?: 0
                        val tithiVal = DhruvankData.tithiDhruvank[selectedTithi] ?: 0
                        val vaarVal = DhruvankData.vaarDhruvank[selectedVaar] ?: 0
                        val rashiVal = DhruvankData.rashiDhruvank[selectedRashi] ?: 0
                        val cityVal = DhruvankData.cityDhruvank[selectedCity] ?: 0

                        val total = commodityVal + nakshatraVal + tithiVal + vaarVal + rashiVal + cityVal
                        val remainder = total % 8

                        val vaarLord = when (selectedVaar) {
                            "रविवार" -> "सूर्य"
                            "सोमवार" -> "चन्द्र"
                            "मंगलवार" -> "मंगल"
                            "बुधवार" -> "बुध"
                            "गुरुवार" -> "गुरु"
                            "शुक्रवार" -> "शुक्र"
                            "शनिवार" -> "शनि"
                            else -> "सूर्य"
                        }

                        val grahaSequence = listOf(
                            "सूर्य", "चन्द्र", "मंगल", "बुध", "गुरु",
                            "शुक्र", "शनि", "राहु", "केतु"
                        )
                        val startIndex = grahaSequence.indexOf(vaarLord)
                        val resultIndex = (startIndex + remainder) % 9
                        val resultGraha = grahaSequence[resultIndex]

                        isTeji = resultGraha in DhruvankData.tejiGraha

                        resultText = "📊 गणना विवरण:\n\n" +
                                "वस्तु ($selectedCommodity): $commodityVal\n" +
                                "नक्षत्र ($selectedNakshatra): $nakshatraVal\n" +
                                "तिथि ($selectedTithi): $tithiVal\n" +
                                "वार ($selectedVaar): $vaarVal\n" +
                                "राशि ($selectedRashi): $rashiVal\n" +
                                "शहर ($selectedCity): $cityVal\n\n" +
                                "कुल योग: $total\n" +
                                "शेष (÷8): $remainder\n" +
                                "फलित ग्रह: $resultGraha"
                        hasResult = true
                    } catch (e: Exception) {
                        resultText = "गणना में त्रुटि: ${e.message}"
                        hasResult = true
                        isTeji = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("गणना करें (Calculate)")
            }

            if (hasResult) {
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
                            text = if (isTeji) "📈 अनुमान: तेजी (Teji)"
                            else "📉 अनुमान: मंदी (Mandi)",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleDropdown(
    label: String,
    items: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(items.firstOrNull() ?: "") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selected)
        }
    }

    if (expanded) {
        AlertDialog(
            onDismissRequest = { expanded = false },
            title = { Text(label) },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                ) {
                    items(items) { item ->
                        TextButton(
                            onClick = {
                                selected = item
                                onSelect(item)
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(item)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { expanded = false }) {
                    Text("बंद करें")
                }
            }
        )
    }
}
