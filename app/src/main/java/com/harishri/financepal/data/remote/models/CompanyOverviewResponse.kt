package com.harishri.financepal.data.remote.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyOverviewResponse(
    @SerialName("Symbol")
    val symbol: String,

    @SerialName("Name")
    val name: String,

    @SerialName("Description")
    val description: String,

    @SerialName("Exchange")
    val exchange: String,

    @SerialName("Currency")
    val currency: String,

    @SerialName("Country")
    val country: String,

    @SerialName("Sector")
    val sector: String,

    @SerialName("Industry")
    val industry: String,

    @SerialName("MarketCapitalization")
    val marketCapitalization: String,

    @SerialName("PERatio")
    val peRatio: String,

    @SerialName("EPS")
    val eps: String,

    @SerialName("52WeekHigh")
    val fiftyTwoWeekHigh: String,

    @SerialName("52WeekLow")
    val fiftyTwoWeekLow: String
)