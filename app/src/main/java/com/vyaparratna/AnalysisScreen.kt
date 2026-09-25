package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
                            Icons.AutoMirrored.Filled.ArrowBack,
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
            Text(
                "विवरण भरें",
                style = MaterialTheme.typography.titleLarge
            )

            DropdownSelector(
                label = "वस्तु चुनें",
                items = DhruvankData.commodityDhruvank.keys.toList()
            ) { selectedCommodity = it }

            DropdownSelector(
                label = "नक्षत्र चुनें",
                items = DhruvankData.nakshatraDhruvank.keys.toList()
            ) { selectedNakshatra = it }

            DropdownSelector(
                label = "तिथि चुनें",
                items = DhruvankData.tithiDhruvank.keys.toList()
            ) { selectedTithi = it }

            DropdownSelector(
                label = "वार चुनें",
                items = DhruvankData.vaarDhruvank.keys.toList()
            ) { selectedVaar = it }

            DropdownSelector(
                label = "राशि चुनें",
                items = DhruvankData.rashiDhruvank.keys.toList()
            ) { selectedRashi = it }

            DropdownSelector(
                label = "शहर चुनें",
                items = DhruvankData.cityDhruvank.keys.toList()
            ) { selectedCity = it }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
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

                    val grahaSequence = listOf("सूर्य", "चन्द्र", "मंगल", "बुध", "गुरु", "शुक्र", "शनि", "राहु", "केतु")
                    val startIndex = grahaSequence.indexOf(vaarLord)
                    val resultIndex = (startIndex + remainder) % 9
                    val resultGraha = grahaSequence[resultIndex]

                    isTeji = resultGraha in DhruvankData.tejiGraha

                    resultText = buildString {
                        append("📊 गणना विवरण:\n\n")
                        append("वस्तु ($selectedCommodity): $commodityVal\n")
                        append("नक्षत्र ($selectedNakshatra): $nakshatraVal\n")
                        append("तिथि ($selectedTithi): $tithiVal\n")
                        append("वार ($selectedVaar): $vaarVal\n")
                        append("राशि ($selectedRashi): $rashiVal\n")
                        append("शहर ($selectedCity): $cityVal\n\n")
                        append("कुल योग: $total\n")
                        append("शेष (÷8): $remainder\n")
                        append("फलित ग्रह: $resultGraha\n")
                    }
                    hasResult = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("गणना करें (Calculate)", style = MaterialTheme.typography.titleMedium)
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
                            text = if (isTeji) "📈 अनुमान: तेजी (Teji)" else "📉 अनुमान: मंदी (Mandi)",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(items.firstOrNull() ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selected = item
                        onSelect(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
