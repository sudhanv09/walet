package io.github.sudhanv09.domain.repository

import io.github.sudhanv09.domain.model.SavingBucket
import kotlinx.coroutines.flow.Flow

interface SavingBucketRepository {
    fun getAllSavingBuckets(): Flow<List<SavingBucket>>
    suspend fun getSavingBucketById(id: Long): SavingBucket?
    suspend fun insertSavingBucket(bucket: SavingBucket): Long
    suspend fun updateSavingBucket(bucket: SavingBucket)
    suspend fun deleteSavingBucket(bucket: SavingBucket)
    suspend fun addToBucket(bucketId: Long, amount: Double)
}
