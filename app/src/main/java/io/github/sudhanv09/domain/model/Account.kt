package io.github.sudhanv09.domain.model

enum class AccountType {
    CASH, BANK, CREDIT_CARD
}

data class Account(
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val balance: Double,
    val color: Int,
    val icon: String? = null,
    val creditLimit: Double? = null,
    val billingDate: Int? = null,
    val isDefault: Boolean = false
)
