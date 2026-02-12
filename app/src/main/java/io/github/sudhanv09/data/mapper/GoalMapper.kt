package io.github.sudhanv09.data.mapper

import io.github.sudhanv09.data.local.entity.GoalEntity
import io.github.sudhanv09.domain.model.Goal

fun Goal.toEntity(): GoalEntity = GoalEntity(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    deadline = deadline,
    icon = icon,
    color = color
)

fun GoalEntity.toDomain(): Goal = Goal(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    deadline = deadline,
    icon = icon,
    color = color
)
