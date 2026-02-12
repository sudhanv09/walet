package io.github.sudhanv09.domain.model

data class SavingBucket(
    val id: Long = 0,
    val name: String,
    val targetAmount: Double? = null,
    val currentAmount: Double = 0.0,
    val icon: String,
    val color: Int
)
