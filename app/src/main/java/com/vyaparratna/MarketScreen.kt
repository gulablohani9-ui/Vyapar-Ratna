package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
    var selectedNakshatra by remember { mutableStateOf("अश्विनी") }
    var selectedTithi by remember { mutableStateOf("प्रतिपदा") }
    var selectedVaar by remember { mutableStateOf("रविवार") }
    var selectedRashi by remember { mutableStateOf("मेष") }
    var selectedCity by remember { mutableStateOf("दिल्ली") }
    var priceInput by remember { mutableStateOf("") }

    var resultText by remember { mutableStateOf("") }
    var predictedPrice by remember { mutableStateOf("") }
    var isTeji by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

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
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            SectionTitle("📊 बाजार चुनें")
            ChipRow(
                items = MarketData.marketDhruvank.keys.toList(),
                selected = selectedMarket,
                onSelect = { selectedMarket = it }
            )

            SectionTitle("⭐ नक्षत्र चुनें")
            ChipRow(
                items = DhruvankData.nakshatraDhruvank.keys.toList(),
                selected = selectedNakshatra,
                onSelect = { selectedNakshatra = it }
            )

            SectionTitle("🌙 तिथि चुनें")
            ChipRow(
                items = DhruvankData.tithiDhruvank.keys.toList(),
                selected = selectedTithi,
                onSelect = { selectedTithi = it }
            )

            SectionTitle("📅 वार चुनें")
            ChipRow(
                items = DhruvankData.vaarDhruvank.keys.toList(),
                selected = selectedVaar,
                onSelect = { selectedVaar = it }
            )

            SectionTitle("♈ राशि चुनें")
            ChipRow(
                items = DhruvankData.rashiDhruvank.keys.toList(),
                selected = selectedRashi,
                onSelect = { selectedRashi = it }
            )

            SectionTitle("🏙️ शहर चुनें")
            ChipRow(
                items = DhruvankData.cityDhruvank.keys.toList(),
                selected = selectedCity,
                onSelect = { selectedCity = it }
            )

            SectionTitle("💰 आज का भाव")
            OutlinedTextField(
                value = priceInput,
                onValueChange = { priceInput = it },
                label = { Text("जैसे: 75000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    try {
                        val marketVal = MarketData.marketDhruvank[selectedMarket] ?: 0
                        val nakshatraVal = DhruvankData.nakshatraDhruvank[selectedNakshatra] ?: 0
                        val tithiVal = DhruvankData.tithiDhruvank[selectedTithi] ?: 0
                        val vaarVal = DhruvankData.vaarDhruvank[selectedVaar] ?: 0
                        val rashiVal = DhruvankData.rashiDhruvank[selectedRashi] ?: 0
                        val cityVal = DhruvankData.cityDhruvank[selectedCity] ?: 0

                        val total = marketVal + nakshatraVal + tithiVal +
                                vaarVal + rashiVal + cityVal
                        val remainder = total % 8

                        isTeji = remainder % 2 == 0

                        val price = priceInput.toDoubleOrNull() ?: 0.0
                        val changePercent = if (isTeji) {
                            1.0 + (remainder * 0.5)
                        } else {
                            1.0 - (remainder * 0.5)
                        }
                        val predicted = price * changePercent
                        val change = predicted - price

                        predictedPrice = if (price > 0) {
                            "अनुमानित भाव: ₹%.2f\n".format(predicted) +
                                    "बदलाव: ₹%.2f (%.2f%%)".format(
                                        change,
                                        (change / price) * 100
                                    )
                        } else {
                            "कृपया मूल्य डालें"
                        }

                        resultText = "📊 विवरण:\n" +
                                "बाजार: $selectedMarket ($marketVal)\n" +
                                "नक्षत्र: $selectedNakshatra ($nakshatraVal)\n" +
                                "तिथि: $selectedTithi ($tithiVal)\n" +
                                "वार: $selectedVaar ($vaarVal)\n" +
                                "राशि: $selectedRashi ($rashiVal)\n" +
                                "शहर: $selectedCity ($cityVal)\n\n" +
                                "कुल योग: $total\n" +
                                "शेष (÷8): $remainder"

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
                    "🔮 गणना करें",
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
                            text = if (isTeji) "📈 तेजी (Teji)" else "📉 मंदी (Mandi)",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.bodyMedium
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
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun ChipRow(
    items: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            FilterChip(
                selected = selected == item,
                onClick = { onSelect(item) },
                label = { Text(item) }
            )
        }
    }
}
