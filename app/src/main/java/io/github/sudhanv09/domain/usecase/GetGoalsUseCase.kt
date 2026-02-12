package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Goal
import io.github.sudhanv09.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGoalsUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    operator fun invoke(): Flow<List<Goal>> = repository.getAllGoals()
}
