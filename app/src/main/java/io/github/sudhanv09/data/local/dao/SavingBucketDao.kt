package io.github.sudhanv09.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.sudhanv09.data.local.entity.SavingBucketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingBucketDao {
    @Query("SELECT * FROM saving_buckets ORDER BY name ASC")
    fun getAllSavingBuckets(): Flow<List<SavingBucketEntity>>

    @Query("SELECT * FROM saving_buckets WHERE id = :id")
    suspend fun getSavingBucketById(id: Long): SavingBucketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingBucket(bucket: SavingBucketEntity): Long

    @Update
    suspend fun updateSavingBucket(bucket: SavingBucketEntity)

    @Delete
    suspend fun deleteSavingBucket(bucket: SavingBucketEntity)

    @Query("UPDATE saving_buckets SET currentAmount = currentAmount + :amount WHERE id = :bucketId")
    suspend fun addToBucket(bucketId: Long, amount: Double)
}
