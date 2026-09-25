package com.vyaparratna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {  // 👈 YAHAN CHANGE HAI
    val menuItems = listOf(
        "🪐 ग्रह गोचर (Transit)" to "transit",
        "🔮 सर्वतोभद्रचक्र" to "chakra",
        "📊 तेजी-मंदी विश्लेषण" to "analysis",
        "📖 ध्रुवांक सारिणी" to "dhruvank",
        "📅 पंचांग" to "panchang",
        "🎯 वेध-पद्धति" to "vedha",
        "🧮 Calculation Tools" to "tools"
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("व्यापार रत्न") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(menuItems) { (item, route) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (route == "dhruvank") {
                            navController.navigate("dhruvank")
                        }
                    }
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
