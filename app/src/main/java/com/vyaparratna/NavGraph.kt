package com.vyaparratna

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("dhruvank") { DhruvankScreen(navController) }
        composable("analysis") { MarketScreen(navController) }
        composable("history") { HistoryScreen(navController) }
    }
}
