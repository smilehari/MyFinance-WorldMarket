package com.harishri.financepal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harishri.financepal.presentation.ui.watchlist.domain.model.Stock

//Stock tracking(not owned), Price alerts
//ncludes price alerts setup

@Entity(
    tableName = "watchlist",
    //indices = [Index(value = ["userId", "symbol"], unique = true)]
)
data class WatchlistEntity(
    @PrimaryKey
    val symbol: String,
    val name: String,
    val userId: String,
    val currentPrice: Double = 0.0,
    val previousClose: Double = 0.0,
    val change: Double =0.0,
    val changePercent: Double = 0.0,
    val volume: Long?,
    val marketCap: Long?,
    //TODO add this val exchange: String?="",
    val sortOrder: Int =0,
    val addedAt: Long = System.currentTimeMillis(),
    val lastPriceUpdate: Long = System.currentTimeMillis(),
    val alertEnabled: Boolean = false,     // Price alert enabled
    val targetPrice: Double? = null,       // Alert when price reaches this
    val stopLoss: Double? = null           // Alert when price drops below this
)

fun WatchlistEntity.toDomainModel(): Stock {
    return Stock(
        symbol = symbol,
        name = name,
        currentPrice = currentPrice,
        previousClose = previousClose,

        change = change,
        changePercent = changePercent,
        volume = volume?:0L,
        marketCap = marketCap,
        lastPriceUpdate = lastPriceUpdate
    )
}