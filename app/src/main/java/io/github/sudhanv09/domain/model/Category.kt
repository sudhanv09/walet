package io.github.sudhanv09.domain.model

enum class TransactionType {
    EXPENSE, INCOME, TRANSFER
}

data class Category(
    val id: Long = 0,
    val name: String,
    val icon: String,
    val color: Int,
    val type: TransactionType,
    val isDefault: Boolean = false
)
