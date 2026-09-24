package com.vyaparratna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val menuItems = listOf(
        "🪐 ग्रह गोचर (Transit)",
        "🔮 सर्वतोभद्रचक्र",
        "📊 तेजी-मंदी विश्लेषण",
        "📖 ध्रुवांक सारिणी",
        "📅 पंचांग",
        "🎯 वेध-पद्धति",
        "🧮 Calculation Tools"
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
            items(menuItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { }
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
