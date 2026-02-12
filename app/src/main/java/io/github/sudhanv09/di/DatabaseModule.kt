package io.github.sudhanv09.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.sudhanv09.data.local.dao.AccountDao
import io.github.sudhanv09.data.local.dao.CategoryDao
import io.github.sudhanv09.data.local.dao.GoalDao
import io.github.sudhanv09.data.local.dao.SavingBucketDao
import io.github.sudhanv09.data.local.dao.TransactionDao
import io.github.sudhanv09.data.local.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "walet_database"
        ).build()
    }

    @Provides
    fun provideAccountDao(database: AppDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao()

    @Provides
    fun provideGoalDao(database: AppDatabase): GoalDao = database.goalDao()

    @Provides
    fun provideSavingBucketDao(database: AppDatabase): SavingBucketDao = database.savingBucketDao()
}
