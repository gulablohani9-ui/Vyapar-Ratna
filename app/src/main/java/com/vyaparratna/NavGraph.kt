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
        composable("analysis") { MarketScreen(navController) }
        composable("dhruvank") { DhruvankScreen(navController) }
        composable("history") { HistoryScreen(navController) }
        composable("chakra") { SarvatobhadraScreen(navController) }
        composable("transit") { TransitScreen(navController) }
        composable("panchang") { PanchangScreen(navController) }
        composable("vedha") { VedhaScreen(navController) }
        composable("tools") { ToolsScreen(navController) }
    }
}
