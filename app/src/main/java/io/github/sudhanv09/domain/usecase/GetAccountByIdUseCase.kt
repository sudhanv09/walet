package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(id: Long): Account? = repository.getAccountById(id)
}
