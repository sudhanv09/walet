package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.repository.SavingBucketRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToSavingBucketUseCase @Inject constructor(
    private val repository: SavingBucketRepository
) {
    suspend operator fun invoke(bucketId: Long, amount: Double) = repository.addToBucket(bucketId, amount)
}
