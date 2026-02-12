package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.repository.GoalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(goalId: Long, amount: Double) = repository.addToGoal(goalId, amount)
}
