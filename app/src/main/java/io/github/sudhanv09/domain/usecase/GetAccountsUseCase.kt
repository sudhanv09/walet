package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> = repository.getAllAccounts()
}
