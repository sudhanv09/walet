package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDefaultAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: Long) = repository.setDefaultAccount(accountId)
}
