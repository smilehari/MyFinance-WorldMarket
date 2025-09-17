package com.harishri.financepal.presentation.ui.watchlist
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.harishri.financepal.data.local.entity.WatchlistEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissibleStockItem(
    stock: WatchlistEntity,
    modifier: Modifier = Modifier,
    onRemove: () -> Unit
    ) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd) {
                onRemove()
                true // Confirm dismissal
            } else {
                false // Do not dismiss
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.fillMaxSize(),
        backgroundContent = {
            when (dismissState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                SwipeToDismissBoxValue.Settled -> {}
            }
        }
    ) {
        /*ListItem(
            headlineContent = { Text(stock.name) } ,
            supportingContent = { Text("${stock.currentPrice} ${stock.currentPrice} ${stock.change} (${stock.changePercent})") }
        )*/
        StockListItemContent(
            stockName = stock.name,
            currencyExchange = stock.symbol,
            currentPrice = stock.currentPrice.toString(),
            priceChange = parsePriceChange(stock.change),
            priceChangePercentage = stock.changePercent
        )
    }
}

private fun RowScope.parsePriceChange(change: Double): String {
    return if (change > 0) {
        "+$change"
    } else {
        change.toString()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StockListItemContent(
    stockName: String,
    currencyExchange: String,
    currentPrice: String,
    priceChange: String,
    priceChangePercentage: Double
) {
    val greenColor : Color = Color(0xFF0B6623)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Main Content: Headline and Currency Exchange
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stockName,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currencyExchange,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Price Content: Price, Change, and Percentage
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = currentPrice,
                style = MaterialTheme.typography.bodyLargeEmphasized
            )
            Text(
                text = "$priceChange ($priceChangePercentage%)",
                style = MaterialTheme.typography.bodyLargeEmphasized,
                color = if (priceChange.contains("-")) Color.Red else greenColor
            )
        }
    }
}


/*
@Composable
private fun DismissBackground(
    dismissState: DismissState
) {
    val direction = dismissState.dismissDirection ?: return
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent
            DismissValue.DismissedToEnd -> Color.Green.copy(alpha = 0.8f)
            DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.8f)
        },
        label = "dismiss_background_color"
    )

    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }

    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Done
        DismissDirection.EndToStart -> Icons.Default.Delete
    }

    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
        label = "dismiss_icon_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.scale(scale),
            tint = Color.White
        )
    }
}*/
