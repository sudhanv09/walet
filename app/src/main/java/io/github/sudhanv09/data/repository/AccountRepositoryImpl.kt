package io.github.sudhanv09.data.repository

import io.github.sudhanv09.data.local.dao.AccountDao
import io.github.sudhanv09.data.mapper.toDomain
import io.github.sudhanv09.data.mapper.toEntity
import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getAllAccounts(): Flow<List<Account>> =
        accountDao.getAllAccounts().map { it.map { entity -> entity.toDomain() } }

    override suspend fun getAccountById(id: Long): Account? =
        accountDao.getAccountById(id)?.toDomain()

    override suspend fun getDefaultAccount(): Account? =
        accountDao.getDefaultAccount()?.toDomain()

    override suspend fun insertAccount(account: Account): Long =
        accountDao.insertAccount(account.toEntity())

    override suspend fun updateAccount(account: Account) =
        accountDao.updateAccount(account.toEntity())

    override suspend fun deleteAccount(account: Account) =
        accountDao.deleteAccount(account.toEntity())

    override suspend fun setDefaultAccount(accountId: Long) {
        accountDao.clearDefaultAccount()
        accountDao.getAccountById(accountId)?.let { account ->
            accountDao.updateAccount(account.copy(isDefault = true))
        }
    }

    override suspend fun updateBalance(accountId: Long, amount: Double) =
        accountDao.updateBalance(accountId, amount)
}
