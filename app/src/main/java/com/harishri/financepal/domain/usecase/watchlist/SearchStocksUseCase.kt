package com.harishri.financepal.domain.usecase.watchlist

import com.harishri.financepal.data.remote.models.StockSearchResult
import com.harishri.financepal.domain.repositories.WatchlistRepository
import javax.inject.Inject
import com.harishri.financepal.data.remote.utils.Result

class SearchStocksUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {
    /**
     * Executes the stock search operation.
     * @param query The search query.
     * @return A `Result` object containing the search response.
     */
    suspend operator fun invoke(query: String): Result<List<StockSearchResult>> {
        return watchlistRepository.searchStocks(query)
    }
}