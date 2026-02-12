package io.github.sudhanv09.data.repository

import io.github.sudhanv09.data.local.dao.GoalDao
import io.github.sudhanv09.data.mapper.toDomain
import io.github.sudhanv09.data.mapper.toEntity
import io.github.sudhanv09.domain.model.Goal
import io.github.sudhanv09.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao
) : GoalRepository {
    override fun getAllGoals(): Flow<List<Goal>> =
        goalDao.getAllGoals().map { it.map { entity -> entity.toDomain() } }

    override suspend fun getGoalById(id: Long): Goal? =
        goalDao.getGoalById(id)?.toDomain()

    override suspend fun insertGoal(goal: Goal): Long =
        goalDao.insertGoal(goal.toEntity())

    override suspend fun updateGoal(goal: Goal) =
        goalDao.updateGoal(goal.toEntity())

    override suspend fun deleteGoal(goal: Goal) =
        goalDao.deleteGoal(goal.toEntity())

    override suspend fun addToGoal(goalId: Long, amount: Double) =
        goalDao.addToGoal(goalId, amount)
}
