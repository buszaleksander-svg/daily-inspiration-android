package com.example.dailyinspiration.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.dailyinspiration.Quote
import com.example.dailyinspiration.QuoteID
import com.example.dailyinspiration.inspirationalQuotes

@Composable
fun FavoritesScreen(navController: NavController) {
    val context = LocalContext.current

    val favoriteIds = remember {
        mutableStateListOf<QuoteID>().apply {
            addAll(loadIds(context, "favoriteQuoteIDs"))
        }
    }

    val favoriteQuotes = favoriteIds.mapNotNull { id ->
        inspirationalQuotes[id]?.let { quote -> id to quote }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0F8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF7A7A7A),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.popBackStack() }
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Favorites",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E2E2E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (favoriteQuotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(vertical = 40.dp, horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite quotes yet.",
                        fontSize = 16.sp,
                        color = Color(0xFF7A7A7A)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favoriteQuotes) { (id, quote) ->
                        FavoriteQuoteCard(
                            quote = quote,
                            onRemoveClick = {
                                favoriteIds.remove(id)
                                saveIds(context, "favoriteQuoteIDs", favoriteIds)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteQuoteCard(
    quote: Quote,
    onRemoveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "\"${quote.quote}\"",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2E2E2E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "— ${quote.author}",
                fontSize = 14.sp,
                color = Color(0xFF7A7A7A)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFB36CFF),
                                    Color(0xFF4A90E2)
                                )
                            )
                        )
                        .clickable { onRemoveClick() }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Remove",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Remove",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

private fun loadIds(context: Context, key: String): MutableList<QuoteID> {
    val prefs = context.getSharedPreferences("DailyInspiration", Context.MODE_PRIVATE)
    val raw = prefs.getString(key, "") ?: ""
    if (raw.isBlank()) return mutableListOf()
    return raw.split(",").mapNotNull { it.trim().toIntOrNull() }.toMutableList()
}

private fun saveIds(context: Context, key: String, ids: List<QuoteID>) {
    context.getSharedPreferences("DailyInspiration", Context.MODE_PRIVATE)
        .edit()
        .putString(key, ids.joinToString(","))
        .apply()
}