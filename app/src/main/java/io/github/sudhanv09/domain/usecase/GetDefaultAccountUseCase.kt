package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDefaultAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke() = repository.getDefaultAccount()
}
