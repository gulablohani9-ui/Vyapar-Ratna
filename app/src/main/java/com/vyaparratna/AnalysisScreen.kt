package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(navController: NavHostController) {
    var selectedCommodity by remember { mutableStateOf("गेहूँ") }
    var selectedNakshatra by remember { mutableStateOf("अश्विनी") }
    var selectedTithi by remember { mutableStateOf("प्रतिपदा") }
    var selectedVaar by remember { mutableStateOf("रविवार") }
    var selectedCity by remember { mutableStateOf("दिल्ली") }
    var resultText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("तेजी-मंदी विश्लेषण") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            // Dropdowns for selections
            DropdownSelector("वस्तु चुनें", DhruvankData.commodityDhruvank.keys.toList()) { selectedCommodity = it }
            DropdownSelector("नक्षत्र चुनें", DhruvankData.nakshatraDhruvank.keys.toList()) { selectedNakshatra = it }
            DropdownSelector("तिथि चुनें", DhruvankData.tithiDhruvank.keys.toList()) { selectedTithi = it }
            DropdownSelector("वार चुनें", DhruvankData.vaarDhruvank.keys.toList()) { selectedVaar = it }
            DropdownSelector("शहर चुनें", DhruvankData.cityDhruvank.keys.toList()) { selectedCity = it }

            Button(
                onClick = {
                    val sum = (DhruvankData.commodityDhruvank[selectedCommodity] ?: 0) +
                              (DhruvankData.nakshatraDhruvank[selectedNakshatra] ?: 0) +
                              (DhruvankData.tithiDhruvank[selectedTithi] ?: 0) +
                              (DhruvankData.vaarDhruvank[selectedVaar] ?: 0) +
                              (DhruvankData.cityDhruvank[selectedCity] ?: 0)
                    
                    val remainder = sum % 8
                    val prediction = if (remainder % 2 == 0) "📈 तेजी (Teji)" else "📉 मंदी (Mandi)"
                    
                    resultText = "कुल योग: $sum\nशेष (Remainder): $remainder\n\nअनुमान: $prediction"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("गणना करें (Calculate)")
            }

            if (resultText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(label: String, items: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(items.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
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
