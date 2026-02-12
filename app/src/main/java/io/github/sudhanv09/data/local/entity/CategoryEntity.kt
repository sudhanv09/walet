package io.github.sudhanv09.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sudhanv09.domain.model.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String,
    val color: Long,
    val type: TransactionType,
    val isDefault: Boolean = false
)
