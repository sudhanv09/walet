package io.github.sudhanv09.data.repository

import io.github.sudhanv09.data.local.dao.TransactionDao
import io.github.sudhanv09.data.mapper.toDomain
import io.github.sudhanv09.data.mapper.toEntity
import io.github.sudhanv09.domain.model.Transaction
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override fun getAllTransactions(): Flow<List<Transaction>> =
        transactionDao.getAllTransactions().map { it.map { entity -> entity.toDomain() } }

    override fun getTransactionsByAccount(accountId: Long): Flow<List<Transaction>> =
        transactionDao.getTransactionsByAccount(accountId).map { it.map { entity -> entity.toDomain() } }

    override fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>> =
        transactionDao.getTransactionsByCategory(categoryId).map { it.map { entity -> entity.toDomain() } }

    override fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> =
        transactionDao.getTransactionsByType(type).map { it.map { entity -> entity.toDomain() } }

    override fun getTransactionsBetween(startTime: Long, endTime: Long): Flow<List<Transaction>> =
        transactionDao.getTransactionsBetween(startTime, endTime).map { it.map { entity -> entity.toDomain() } }

    override suspend fun getTransactionById(id: Long): Transaction? =
        transactionDao.getTransactionById(id)?.toDomain()

    override suspend fun insertTransaction(transaction: Transaction): Long =
        transactionDao.insertTransaction(transaction.toEntity())

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDao.updateTransaction(transaction.toEntity())

    override suspend fun deleteTransaction(transaction: Transaction) =
        transactionDao.deleteTransaction(transaction.toEntity())

    override fun getTotalByTypeBetween(type: TransactionType, startTime: Long, endTime: Long): Flow<Double?> =
        transactionDao.getTotalByTypeBetween(type, startTime, endTime)
}
