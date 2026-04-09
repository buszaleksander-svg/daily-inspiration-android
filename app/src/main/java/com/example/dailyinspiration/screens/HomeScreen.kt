package com.example.dailyinspiration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0F8))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(180.dp)) // 👈 kontrolujemy ręcznie

            Text(
                text = "Daily",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2E2E2E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Inspiration",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB36CFF),
                            Color(0xFF4A90E2)
                        )
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "One thought. One moment. Just for you.",
                fontSize = 14.sp,
                color = Color(0xFF7A7A7A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(140.dp)) // 👈 tu ustawiasz button

            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(44.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFB36CFF),
                                Color(0xFF4A90E2)
                            )
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .clickable {
                        navController.navigate("inspiration")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Inspire me",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}