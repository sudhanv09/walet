package io.github.sudhanv09.data.repository

import io.github.sudhanv09.data.local.dao.SavingBucketDao
import io.github.sudhanv09.data.mapper.toDomain
import io.github.sudhanv09.data.mapper.toEntity
import io.github.sudhanv09.domain.model.SavingBucket
import io.github.sudhanv09.domain.repository.SavingBucketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavingBucketRepositoryImpl @Inject constructor(
    private val savingBucketDao: SavingBucketDao
) : SavingBucketRepository {
    override fun getAllSavingBuckets(): Flow<List<SavingBucket>> =
        savingBucketDao.getAllSavingBuckets().map { it.map { entity -> entity.toDomain() } }

    override suspend fun getSavingBucketById(id: Long): SavingBucket? =
        savingBucketDao.getSavingBucketById(id)?.toDomain()

    override suspend fun insertSavingBucket(bucket: SavingBucket): Long =
        savingBucketDao.insertSavingBucket(bucket.toEntity())

    override suspend fun updateSavingBucket(bucket: SavingBucket) =
        savingBucketDao.updateSavingBucket(bucket.toEntity())

    override suspend fun deleteSavingBucket(bucket: SavingBucket) =
        savingBucketDao.deleteSavingBucket(bucket.toEntity())

    override suspend fun addToBucket(bucketId: Long, amount: Double) =
        savingBucketDao.addToBucket(bucketId, amount)
}
