//  Created by Mats Rune Bergman on 2026-01-30. Remade in KT by Carlo 2026-04-10
//

package com.example.dailyinspiration

typealias QuoteID = Int

data class Quote(val quote: String, val author: String)

val inspirationalQuotes: Map<QuoteID, Quote> = mapOf(
    1  to Quote("The only way to do great work is to love what you do.", "Steve Jobs"),
    2  to Quote("The way to get started is to quit talking and begin doing.", "Walt Disney"),
    3  to Quote("The future belongs to those who believe in the beauty of their dreams.", "Eleanor Roosevelt"),
    4  to Quote("It does not matter how slowly you go as long as you do not stop.", "Confucius"),
    5  to Quote("Life is like riding a bicycle. To keep your balance, you must keep moving.", "Albert Einstein"),
    6  to Quote("You will face many defeats in life, but never let yourself be defeated.", "Maya Angelou"),
    7  to Quote("It always seems impossible until it's done.", "Nelson Mandela"),
    8  to Quote("Whether you think you can or you think you can't, you're right.", "Henry Ford"),
    9  to Quote("Don't watch the clock; do what it does. Keep going.", "Unknown"),
    10 to Quote("Setting goals is the first step in turning the invisible into the visible.", "Tony Robbins")
)