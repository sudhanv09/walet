package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Transaction
import io.github.sudhanv09.domain.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Long): Transaction? = repository.getTransactionById(id)
}
