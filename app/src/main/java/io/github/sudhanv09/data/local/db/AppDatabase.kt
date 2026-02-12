package io.github.sudhanv09.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.sudhanv09.data.local.dao.AccountDao
import io.github.sudhanv09.data.local.dao.CategoryDao
import io.github.sudhanv09.data.local.dao.GoalDao
import io.github.sudhanv09.data.local.dao.SavingBucketDao
import io.github.sudhanv09.data.local.dao.TransactionDao
import io.github.sudhanv09.data.local.entity.AccountEntity
import io.github.sudhanv09.data.local.entity.CategoryEntity
import io.github.sudhanv09.data.local.entity.GoalEntity
import io.github.sudhanv09.data.local.entity.SavingBucketEntity
import io.github.sudhanv09.data.local.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        GoalEntity::class,
        SavingBucketEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun goalDao(): GoalDao
    abstract fun savingBucketDao(): SavingBucketDao
}
