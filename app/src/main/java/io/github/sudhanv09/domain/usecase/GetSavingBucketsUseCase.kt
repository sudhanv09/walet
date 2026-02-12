package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.SavingBucket
import io.github.sudhanv09.domain.repository.SavingBucketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSavingBucketsUseCase @Inject constructor(
    private val repository: SavingBucketRepository
) {
    operator fun invoke(): Flow<List<SavingBucket>> = repository.getAllSavingBuckets()
}
