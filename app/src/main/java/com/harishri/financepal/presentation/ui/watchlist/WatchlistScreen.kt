package com.harishri.financepal.presentation.ui.watchlist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harishri.financepal.data.local.entity.WatchlistEntity
import com.harishri.financepal.data.remote.models.StockSearchResult
import com.harishri.financepal.presentation.ui.watchlist.domain.model.Stock
import com.harishri.financepal.presentation.viewmodels.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar with Search
        WatchlistTopBar(
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::updateSearchQuery,
            isSearching = uiState.isSearching
        )

        // Show error message if any
        uiState.error?.let { error ->
            ErrorMessage(
                message = error,
                onDismiss = viewModel::clearError
            )
        }

        // Main content
        Box(modifier = Modifier.weight(1f)) {
            when {
                // Show search results when searching
                searchQuery.isNotBlank() && searchResults.isNotEmpty() -> {
                    SearchResultsList(
                        searchResults = searchResults,
                        onAddStock = viewModel::addStockToWatchlist
                    )
                }

                // Show watchlist when not searching
                uiState.stocks.isNotEmpty() -> {
                    //populate the watchlist
                    WatchlistContent(
                        stocks = uiState.stocks,
                        onRemoveStock = viewModel::removeStockFromWatchlist
                    )
                }

                // Show empty state
                !uiState.isLoading -> {
                    EmptyWatchlistState()
                }
            }

            // Loading indicator
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearching: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                text = "Watchlist",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search stocks by symbol...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(
                            onClick = { onSearchQueryChange("") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    } else if (isSearching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }else if(!isSearching){
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }
    }
}

@Composable
fun SearchResultsList(
    searchResults: List<StockSearchResult>,
    onAddStock: (String,String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(searchResults) { result ->
            SearchResultItem(
                result = result,
                onAddClick = { onAddStock(result.symbol,result.name) }
            )
        }
    }
}

@Composable
fun SearchResultItem(
    result: StockSearchResult,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAddClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = result.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = result.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${result.type} • ${result.region}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to watchlist",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun WatchlistContent(
    stocks: List<WatchlistEntity>,
    onRemoveStock: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = stocks,
            key = { it.symbol }
        ) { stock ->
            SwipeableStockItem(
                stock = stock,
                onRemove = { onRemoveStock(stock.symbol) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SwipeableStockItem(
    stock: WatchlistEntity,
    onRemove: () -> Unit,
    modifier: Modifier
) {
    // You can choose any of the three swipe implementations:
    // Option 1: Material3 SwipeToDismiss (Recommended)
    DismissibleStockItem(
        stock = stock,
        modifier = modifier,
        onRemove = onRemove
    )

}

@Composable
fun StockItem(stock: Stock) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stock symbol and name
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stock.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stock.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Price and change
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${String.format("%.2f", stock.currentPrice)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Price change with color
                val changeColor = if (stock.isPositive) {
                    Color(0xFF4CAF50) // Green
                } else {
                    Color(0xFFF44336) // Red
                }

                Text(
                    text = "${if (stock.isPositive) "+" else ""}${String.format("%.2f", stock.change)} (${String.format("%.2f", stock.changePercent)}%)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = changeColor
                )
            }
        }
    }
}

@Composable
fun EmptyWatchlistState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No stocks in watchlist",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Search and add stocks to start tracking their prices",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

/*
fun WatchlistScreen(viewModel: WatchlistViewModel) {
    val watchlistState by viewModel.watchlistState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(viewModel = viewModel, searchState = searchState)

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = watchlistState) {
            is WatchlistState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is WatchlistState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = "Your watchlist is empty. Add a stock to get started.")
                }
            }
            is WatchlistState.Success -> {
                if (state.stocks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Your watchlist is empty. Add a stock to get started.")
                    }
                } else {
                    WatchlistContent(stocks = state.stocks)
                }
            }
            is WatchlistState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = "Error: ${state.message}")
                }
            }
        }
    }
}

*/
/**
 * A search bar and result list.
 *//*

@Composable
fun SearchBar(viewModel: WatchlistViewModel, searchState: SearchState) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                //TODO : Commented to avoid multiple calls on change during development to not exhaust free quota usage
                searchQuery = it
                */
/*if (it.isNotBlank()) {
                    viewModel.searchStocks(it)
                }*//*

            },
            label = { Text("Search stocks") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    if (searchQuery.isNotBlank()) {
                        viewModel.searchStocks(searchQuery)
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        when (searchState) {
            is SearchState.Searching -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            is SearchState.Success -> {
                Log.d("WatchlistScreen", "SearchBar successful:$$$ ${searchState.response}")
                SearchResultsList(
                    response = searchState.response,
                    onAddClick = { symbol ->
                        viewModel.addStockToWatchlist(symbol)
                        searchQuery = "" // Clear search bar after adding
                        keyboardController?.hide()
                    }
                )
            }
            is SearchState.Error -> {
                Log.e("WatchlistScreen", "SearchBar ERROR:$$$ ${searchState.message}")
                Text(
                    text = "Search Error: ${searchState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                Log.e("WatchlistScreen", "What just happened Bro $$$")
            }
        }
    }
}

*/
/**
 * Displays the search results in a list.
 *//*

@Composable
fun SearchResultsList(response: StockSearchResponse, onAddClick: (String) -> Unit) {
    Log.d("WatchlistScreen", "SearchResultsList successful:$$$ ${response.bestMatches}")
    if (response.bestMatches.isEmpty()) {
        Text("No results found.")
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp) // Limit search results height
        ) {
            items(response.bestMatches) { match ->
                Log.d("WatchlistScreen", "SearchResultsList match ${match.name}")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = match.symbol, fontWeight = FontWeight.Bold)
                    Text(text = match.name)
                    IconButton(onClick = { onAddClick(match.symbol) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}

*/
/**
 * Displays the watchlist content.
 *//*

@Composable
fun WatchlistContent(stocks: List<WatchlistEntity>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(stocks) { stock ->
            WatchlistItem(stock = stock)
        }
    }
}

*/
/**
 * Displays a single watchlist item.
 *//*

@Composable
fun WatchlistItem(stock: WatchlistEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column {
                Text(text = stock.symbol, style = MaterialTheme.typography.titleMedium)
                Text(text = stock.symbol ?: "N/A", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(
                    text = "$${stock.currentPrice}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${stock.changePercent}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}*/
