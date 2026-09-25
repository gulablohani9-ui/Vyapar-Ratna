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
fun ToolsScreen(navController: NavHostController) {
    var inputValue by remember { mutableStateOf("") }
    var resultValue by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("गणना Tools") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "🧮 विंशोपक Calculator",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "किसी भी ध्रुवांक को डालें, 8 से भाग देकर शेष निकालें",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                label = { Text("ध्रुवांक डालें") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    val num = inputValue.toIntOrNull()
                    if (num != null) {
                        val remainder = num % 8
                        val prediction = if (remainder % 2 == 0) "📈 तेजी" else "📉 मंदी"
                        resultValue = "संख्या: $num\nशेष (÷8): $remainder\n\n$prediction"
                    } else {
                        resultValue = "कृपया वैध संख्या डालें"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("गणना करें")
            }

            if (resultValue.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Text(
                        text = resultValue,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "📖 उपयोगी जानकारी",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "• शेष 1, 3, 5, 7 → मंदी\n" +
                                "• शेष 0, 2, 4, 6 → तेजी\n" +
                                "• ध्रुवांक Book Page 14 से लें\n" +
                                "• तेजी-मंदी विश्लेषण में सभी factors जोड़ें",
                        style = MaterialTheme.typography.bodyMedium
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
