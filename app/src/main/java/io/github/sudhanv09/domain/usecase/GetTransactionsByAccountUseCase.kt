package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Transaction
import io.github.sudhanv09.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTransactionsByAccountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(accountId: Long): Flow<List<Transaction>> =
        repository.getTransactionsByAccount(accountId)
}
