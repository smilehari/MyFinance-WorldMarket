package com.harishri.financepal.data.local.entity.enums

enum class RecurringPeriod {
    WEEKLY, MONTHLY, QUARTERLY, YEARLY;

    fun getDisplayName(): String = when (this) {
        WEEKLY -> "Weekly"
        MONTHLY -> "Monthly"
        QUARTERLY -> "Quarterly"
        YEARLY -> "Yearly"
    }

    fun getDaysInterval(): Int = when (this) {
        WEEKLY -> 7
        MONTHLY -> 30
        QUARTERLY -> 90
        YEARLY -> 365
    }
}