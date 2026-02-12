package io.github.sudhanv09.domain.model

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val dateTime: Long,
    val description: String? = null,
    val photoUri: String? = null,
    val categoryId: Long,
    val accountId: Long,
    val toAccountId: Long? = null,
    val type: TransactionType
)
