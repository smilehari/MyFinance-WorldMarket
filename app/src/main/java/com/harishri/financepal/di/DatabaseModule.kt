package com.harishri.financepal.di

import android.content.Context
import androidx.room.Room
import com.harishri.financepal.data.local.datasource.LocalUserDataSource
import com.harishri.financepal.data.local.MyStockWorldMarketDatabase
import com.harishri.financepal.data.local.dao.*
import com.harishri.financepal.data.local.datasource.WatchlistLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMyStockWorldMarketDatabase(@ApplicationContext context: Context): MyStockWorldMarketDatabase {
        return Room.databaseBuilder(
            context,
            MyStockWorldMarketDatabase::class.java,
            "finance_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideUserDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): UserDao {
        return myStockWorldMarketDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideWatchlistDao (myStockWorldMarketDatabase: MyStockWorldMarketDatabase): WatchlistDao {
        return myStockWorldMarketDatabase.watchlistDao()
    }

    @Provides
    @Singleton
    fun provideLocalUserDataSource( userDao: UserDao): LocalUserDataSource {
        return LocalUserDataSource(userDao)
    }


    @Provides
    @Singleton
    fun provideWatchlistLocalDataSource(watchlistDao: WatchlistDao):WatchlistLocalDataSource{
        return WatchlistLocalDataSource(watchlistDao)
    }



    /*@Provides
    fun provideHoldingDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): HoldingDao {
        return myStockWorldMarketDatabase.holdingDao()
    }

    @Provides
    fun providePortfolioDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): PortfolioDao {
        return myStockWorldMarketDatabase.portfolioDao()
    }

    @Provides
    fun provideTransactionDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): TransactionDao {
        return myStockWorldMarketDatabase.transactionDao()
    }

    @Provides
    fun provideWatchlistDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): WatchlistDao {
        return myStockWorldMarketDatabase.watchlistDao()
    }

    @Provides
    fun provideStockPriceDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): StockPriceDao {
        return myStockWorldMarketDatabase.stockPriceDao()
    }

    @Provides
    fun provideExpenseDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): ExpenseDao {
        return myStockWorldMarketDatabase.expenseDao()
    }

    @Provides
    fun providePriceAlertDao(myStockWorldMarketDatabase: MyStockWorldMarketDatabase): PriceAlertDao {
        return myStockWorldMarketDatabase.priceAlertDao()
    }

    @Provides
    fun providePriceAlertDao(MyStockWorldMarketDatabase: MyStockWorldMarketDatabase): UserDao {
        return myStockWorldMarketDatabase.userDao()
    }*/
}