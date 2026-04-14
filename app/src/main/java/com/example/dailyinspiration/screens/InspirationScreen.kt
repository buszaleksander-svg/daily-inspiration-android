//
//  InspirationScreen.kt
//  DailyInspiration
//
//  Created by Carlo on 2026-04-12.
//

package com.example.dailyinspiration.screens

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyinspiration.QuoteID
import com.example.dailyinspiration.inspirationalQuotes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InspirationScreen(navController: NavController) {

    val context = LocalContext.current
    val scope   = rememberCoroutineScope()

    var selectedID        by remember { mutableStateOf<QuoteID?>(null) }
    var isAnimating       by remember { mutableStateOf(false) }
    var showSavedFeedback by remember { mutableStateOf(false) }

    val animSpec  = tween<Float>(durationMillis = 800)
    val alpha     by animateFloatAsState(if (isAnimating) 1f else 0f,   animSpec, label = "alpha")
    val scale     by animateFloatAsState(if (isAnimating) 1f else 0.8f, animSpec, label = "scale")
    val offsetY   by animateFloatAsState(if (isAnimating) 0f else 30f,  animSpec, label = "offset")

    val iconRotation by animateFloatAsState(
        targetValue   = if (isAnimating) 0f else 180f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f),
        label         = "iconRotation"
    )
    val iconScale by animateFloatAsState(
        targetValue   = if (isAnimating) 1f else 0.5f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f),
        label         = "iconScale"
    )

    LaunchedEffect(Unit) {
        if (selectedID == null) {
            val random = inspirationalQuotes.entries.random()
            selectedID = random.key
            persistSeenHistory(context, random.key)
            delay(100)
            isAnimating = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0F8))
    ) {

        // Top navigation icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp, end = 24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector        = Icons.Filled.Star,
                contentDescription = "Favorites",
                tint               = Color(0xFF7A7A7A),
                modifier           = Modifier
                    .size(24.dp)
                    .clickable { navController.navigate("favorites") }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector        = Icons.Filled.History,
                contentDescription = "History",
                tint               = Color(0xFF7A7A7A),
                modifier           = Modifier
                    .size(24.dp)
                    .clickable { navController.navigate("history") }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(120.dp))

            // Quote card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(vertical = 50.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text     = "✦",
                        fontSize = 40.sp,
                        modifier = Modifier.graphicsLayer {
                            rotationY = iconRotation
                            scaleX    = iconScale
                            scaleY    = iconScale
                        }
                    )

                    val item = selectedID?.let { inspirationalQuotes[it] }

                    if (item != null) {
                        Column(
                            modifier = Modifier.graphicsLayer {
                                this.alpha   = alpha
                                scaleX       = scale
                                scaleY       = scale
                                translationY = offsetY
                            },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text       = "\u201C${item.quote}\u201D",
                                fontSize   = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color      = Color(0xFF2E2E2E),
                                textAlign  = TextAlign.Center
                            )
                            Text(
                                text      = "— ${item.author}",
                                fontSize  = 14.sp,
                                color     = Color(0xFF7A7A7A),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Get Inspired button
            Box(
                modifier = Modifier
                    .width(180.dp)
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
                        // DRAMATISK FADE OUT - 0.7 sekunder
                        scope.launch {
                            isAnimating = false
                            delay(700)

                            val random = inspirationalQuotes.entries.random()
                            selectedID = random.key
                            persistSeenHistory(context, random.key)

                            // DRAMATISK FADE IN - 1.2 sekunder
                            delay(150)
                            isAnimating = true

                            if (showSavedFeedback) showSavedFeedback = false
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "Get Inspired",
                    color      = Color.White,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save to Favorites button
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(44.dp)
                    .shadow(5.dp, RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .clickable {
                        val id = selectedID ?: return@clickable
                        val favorites = loadIds(context, "favoriteQuoteIDs")
                        if (!favorites.contains(id)) {
                            favorites.add(id)
                            saveIds(context, "favoriteQuoteIDs", favorites)
                        }
                        showSavedFeedback = true
                        scope.launch {
                            delay(2000)
                            showSavedFeedback = false
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = if (showSavedFeedback) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint               = if (showSavedFeedback) Color(0xFFFF69B4) else Color(0xFFB36CFF),
                        modifier           = Modifier.size(16.dp)
                    )
                    Text(
                        text       = if (showSavedFeedback) "Saved!" else "Save to Favorites",
                        color      = if (showSavedFeedback) Color(0xFFFF69B4) else Color(0xFFB36CFF),
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun loadIds(context: Context, key: String): MutableList<QuoteID> {
    val prefs = context.getSharedPreferences("DailyInspiration", Context.MODE_PRIVATE)
    val raw   = prefs.getString(key, "") ?: ""
    if (raw.isBlank()) return mutableListOf()
    return raw.split(",").mapNotNull { it.trim().toIntOrNull() }.toMutableList()
}

private fun saveIds(context: Context, key: String, ids: List<QuoteID>) {
    context.getSharedPreferences("DailyInspiration", Context.MODE_PRIVATE)
        .edit()
        .putString(key, ids.joinToString(","))
        .apply()
}

private fun persistSeenHistory(context: Context, id: QuoteID) {
    val history = loadIds(context, "seenQuoteHistory")
    history.remove(id)
    history.add(0, id)
    saveIds(context, "seenQuoteHistory", history)
}