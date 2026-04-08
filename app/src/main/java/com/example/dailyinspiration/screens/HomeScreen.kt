package com.example.dailyinspiration.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Button(onClick = {
        navController.navigate("inspiration")
    }) {
        Text("Inspire Me")
    }
}