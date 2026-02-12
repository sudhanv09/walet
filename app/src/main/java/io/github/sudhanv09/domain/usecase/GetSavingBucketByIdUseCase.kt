package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.SavingBucket
import io.github.sudhanv09.domain.repository.SavingBucketRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSavingBucketByIdUseCase @Inject constructor(
    private val repository: SavingBucketRepository
) {
    suspend operator fun invoke(id: Long): SavingBucket? = repository.getSavingBucketById(id)
}
