package com.harishri.financepal.data.local.entity.enums

enum class AlertType {
    TARGET, STOP_LOSS, PERCENT_CHANGE;

    fun getDisplayName(): String = when (this) {
        TARGET -> "Target Price"
        STOP_LOSS -> "Stop Loss"
        PERCENT_CHANGE -> "Percentage Change"
    }

    fun getDescription(): String = when (this) {
        TARGET -> "Alert when price goes above target"
        STOP_LOSS -> "Alert when price falls below limit"
        PERCENT_CHANGE -> "Alert when price changes by percentage"
    }
}