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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SarvatobhadraScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("सर्वतोभद्रचक्र") })
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
            Text(
                "🔮 सर्वतोभद्रचक्र (81 कोष्ठक)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                "चारों दिशाओं में 28 नक्षत्र और उनके ध्रुवांक:",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4 Directions
            DirectionCard("🔼 उत्तर (North)", listOf(
                "कृत्तिका", "रोहिणी", "मृगशिरा", "आर्द्रा",
                "पुनर्वसु", "पुष्य", "अश्लेषा"
            ))

            DirectionCard("➡️ पूर्व (East)", listOf(
                "मघा", "पूर्वाफाल्गुनी", "उत्तराफाल्गुनी", "हस्त",
                "चित्रा", "स्वाति", "विशाखा"
            ))

            DirectionCard("🔽 दक्षिण (South)", listOf(
                "अनुराधा", "ज्येष्ठा", "मूल", "पूर्वाषाढा",
                "उत्तराषाढा", "श्रवण", "धनिष्ठा"
            ))

            DirectionCard("⬅️ पश्चिम (West)", listOf(
                "शतभिषा", "पूर्वाभाद्रपद", "उत्तराभाद्रपद",
                "रेवती", "अश्विनी", "भरणी", "अभिजित"
            ))

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
fun DirectionCard(title: String, nakshatras: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            nakshatras.forEach { nak ->
                val value = DhruvankData.nakshatraDhruvank[nak] ?: 0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("• $nak", style = MaterialTheme.typography.bodyMedium)
                    Text("$value", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
