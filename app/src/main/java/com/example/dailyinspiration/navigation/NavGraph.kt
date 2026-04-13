package com.example.dailyinspiration.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyinspiration.screens.HomeScreen
import com.example.dailyinspiration.screens.InspirationScreen
import com.example.dailyinspiration.screens.FavoritesScreen
import com.example.dailyinspiration.screens.HistoryScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home")        { HomeScreen(navController) }
        composable("inspiration") { InspirationScreen(navController) }
        composable("favorites")   { FavoritesScreen(navController) }
        composable("history")     { HistoryScreen(navController) }
    }
}
