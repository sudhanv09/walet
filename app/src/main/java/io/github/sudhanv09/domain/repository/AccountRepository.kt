package io.github.sudhanv09.domain.repository

import io.github.sudhanv09.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAccounts(): Flow<List<Account>>
    suspend fun getAccountById(id: Long): Account?
    suspend fun getDefaultAccount(): Account?
    suspend fun insertAccount(account: Account): Long
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun setDefaultAccount(accountId: Long)
    suspend fun updateBalance(accountId: Long, amount: Double)
}
