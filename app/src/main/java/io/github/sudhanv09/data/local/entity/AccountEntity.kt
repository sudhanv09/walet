package io.github.sudhanv09.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sudhanv09.domain.model.AccountType

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val balance: Double,
    val color: Long,
    val icon: String? = null,
    val creditLimit: Double? = null,
    val billingDate: Int? = null,
    val isDefault: Boolean = false
)
