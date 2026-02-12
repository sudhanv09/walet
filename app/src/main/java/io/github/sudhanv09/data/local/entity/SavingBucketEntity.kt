package io.github.sudhanv09.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saving_buckets")
data class SavingBucketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val targetAmount: Double? = null,
    val currentAmount: Double = 0.0,
    val icon: String,
    val color: Int
)
