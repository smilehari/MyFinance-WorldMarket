package com.harishri.financepal.data.local.entity.enums

import androidx.compose.ui.graphics.Color

enum class TransactionType {
    BUY, SELL;

    fun getDisplayName(): String = when (this) {
        BUY -> "Buy"
        SELL -> "Sell"
    }

    fun getColor(): Color = when (this) {
        BUY -> Color.Green
        SELL -> Color.Red
    }
}