package com.harishri.financepal.data.local.entity

//Stop-loss and target price notifications
//Powers your push notification feature
/*
@Entity(
    tableName = "price_alerts",
    foreignKeys = [
        ForeignKey(
            entity = WatchlistEntity::class,
            parentColumns = ["symbol", "userId"],
            childColumns = ["symbol", "userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["symbol"]),
        Index(value = ["isActive"])
    ]
)
data class PriceAlertEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val symbol: String,
    val alertType: AlertType,              // TARGET, STOP_LOSS, PERCENT_CHANGE
    val targetPrice: Double,               // Price to trigger alert
    val currentPrice: Double,              // Price when alert was created
    val message: String,                   // Custom alert message
    val isActive: Boolean = true,
    val isTriggered: Boolean = false,
    val triggeredAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
fun PriceAlertEntity.toDomainModel(): PriceAlert {
    return PriceAlert(
        id = id,
        watchlistItemId = watchlistItemId,
        alertType = AlertType.valueOf(alertType),
        targetPrice = targetPrice,
        isActive = isActive,
        createdAt = createdAt,
        triggeredAt = triggeredAt
    )
}*/
