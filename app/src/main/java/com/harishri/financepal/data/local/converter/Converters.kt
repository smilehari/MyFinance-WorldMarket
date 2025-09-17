package com.harishri.financepal.data.local.converter

import androidx.room.TypeConverter
import com.harishri.financepal.data.local.entity.enums.AlertType
import com.harishri.financepal.data.local.entity.enums.ExpenseCategory
import com.harishri.financepal.data.local.entity.enums.RecurringPeriod
import com.harishri.financepal.data.local.entity.enums.TransactionType

class Converters {
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(type: String): TransactionType = TransactionType.valueOf(type)

    @TypeConverter
    fun fromExpenseCategory(category: ExpenseCategory): String = category.name

    @TypeConverter
    fun toExpenseCategory(category: String): ExpenseCategory = ExpenseCategory.valueOf(category)

    @TypeConverter
    fun fromRecurringPeriod(period: RecurringPeriod?): String? = period?.name

    @TypeConverter
    fun toRecurringPeriod(period: String?): RecurringPeriod? =
        period?.let { RecurringPeriod.valueOf(it) }

    @TypeConverter
    fun fromAlertType(type: AlertType): String = type.name

    @TypeConverter
    fun toAlertType(type: String): AlertType = AlertType.valueOf(type)
}