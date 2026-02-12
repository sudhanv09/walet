package io.github.sudhanv09.domain.model

data class Goal(
    val id: Long = 0,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long? = null,
    val icon: String,
    val color: Int
)
