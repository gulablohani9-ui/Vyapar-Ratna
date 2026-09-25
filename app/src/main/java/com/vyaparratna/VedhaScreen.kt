package com.vyaparratna

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class VedhaInfo(
    val nakshatra: String,
    val direction: String,
    val duration: String,
    val commodity: String,
    val shubhEffect: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VedhaScreen(navController: NavHostController) {
    val vedhaList = listOf(
        VedhaInfo("कृत्तिका", "दक्षिण", "1 मास", "चावल, सोना, हीरा", "मंदी"),
        VedhaInfo("रोहिणी", "पूर्व", "7 दिन", "धान्य, धातु, ऊन", "मंदी"),
        VedhaInfo("मृगशिरा", "उत्तर", "7 दिन", "पशु, धान्य, रत्न", "मंदी"),
        VedhaInfo("आर्द्रा", "पश्चिम", "1 मास", "क्षार, रस, तैल", "मंदी"),
        VedhaInfo("पुनर्वसु", "उत्तर", "60 दिन", "धान्य, सूत, कपास", "मंदी"),
        VedhaInfo("पुष्य", "उत्तर", "8 मास", "सोना, चाँदी, धान्य", "मंदी"),
        VedhaInfo("अश्लेषा", "पश्चिम", "1 मास", "गुड़, शक्कर, धान्य", "मंदी"),
        VedhaInfo("मघा", "दक्षिण", "8 मास", "व्यापारिक वस्तु", "मंदी"),
        VedhaInfo("पूर्वाफाल्गुनी", "दक्षिण", "8 मास", "कम्बल, तिल, वस्त्र", "मंदी"),
        VedhaInfo("उत्तराफाल्गुनी", "उत्तर", "2 मास", "व्यापारिक वस्तु", "मंदी"),
        VedhaInfo("हस्त", "उत्तर", "2 मास", "किराना वस्तु", "मंदी"),
        VedhaInfo("चित्रा", "उत्तर", "2 मास", "धातु, चौपाये", "मंदी"),
        VedhaInfo("स्वाति", "उत्तर", "7 दिन", "किराना वस्तु", "मंदी"),
        VedhaInfo("विशाखा", "दक्षिण", "8 मास", "धान्य", "मंदी")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("वेध-पद्धति") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            Text(
                "🎯 नक्षत्र वेध प्रभाव",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                "शुभ ग्रह वेध → मंदी  |  पाप ग्रह वेध → तेजी",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(vedhaList) { v ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "⭐ ${v.nakshatra}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "दिशा: ${v.direction}  |  अवधि: ${v.duration}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "वस्तु: ${v.commodity}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "शुभ वेध → ${v.shubhEffect}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
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
