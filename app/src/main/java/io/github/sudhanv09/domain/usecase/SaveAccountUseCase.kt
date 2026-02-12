package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account): Long = repository.insertAccount(account)
}
