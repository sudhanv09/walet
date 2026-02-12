package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Goal
import io.github.sudhanv09.domain.repository.GoalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(id: Long): Goal? = repository.getGoalById(id)
}
