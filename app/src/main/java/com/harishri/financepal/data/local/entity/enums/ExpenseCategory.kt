package com.harishri.financepal.data.local.entity.enums

enum class ExpenseCategory {
    PERSONAL, SPOUSE, KIDS, ENTERTAINMENT,
    TRAVEL, FOOD, LOANS, INVESTMENT,
    UTILITIES, HEALTHCARE, EDUCATION, OTHER;

    fun getDisplayName(): String = when (this) {
        PERSONAL -> "Personal"
        SPOUSE -> "Spouse"
        KIDS -> "Kids"
        ENTERTAINMENT -> "Entertainment"
        TRAVEL -> "Travel"
        FOOD -> "Food & Dining"
        LOANS -> "Loans & EMI"
        INVESTMENT -> "Investment"
        UTILITIES -> "Utilities"
        HEALTHCARE -> "Healthcare"
        EDUCATION -> "Education"
        OTHER -> "Other"
    }

    fun getIcon(): String = when (this) {
        PERSONAL -> "👤"
        SPOUSE -> "💑"
        KIDS -> "👶"
        ENTERTAINMENT -> "🎬"
        TRAVEL -> "✈️"
        FOOD -> "🍽️"
        LOANS -> "🏦"
        INVESTMENT -> "📈"
        UTILITIES -> "⚡"
        HEALTHCARE -> "🏥"
        EDUCATION -> "📚"
        OTHER -> "📂"
    }
}