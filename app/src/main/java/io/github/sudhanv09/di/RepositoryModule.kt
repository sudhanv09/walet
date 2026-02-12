package io.github.sudhanv09.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sudhanv09.data.repository.AccountRepositoryImpl
import io.github.sudhanv09.data.repository.CategoryRepositoryImpl
import io.github.sudhanv09.data.repository.GoalRepositoryImpl
import io.github.sudhanv09.data.repository.SavingBucketRepositoryImpl
import io.github.sudhanv09.data.repository.TransactionRepositoryImpl
import io.github.sudhanv09.domain.repository.AccountRepository
import io.github.sudhanv09.domain.repository.CategoryRepository
import io.github.sudhanv09.domain.repository.GoalRepository
import io.github.sudhanv09.domain.repository.SavingBucketRepository
import io.github.sudhanv09.domain.repository.TransactionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(impl: GoalRepositoryImpl): GoalRepository

    @Binds
    @Singleton
    abstract fun bindSavingBucketRepository(impl: SavingBucketRepositoryImpl): SavingBucketRepository
}
