package io.github.sudhanv09.data.mapper

import io.github.sudhanv09.data.local.entity.SavingBucketEntity
import io.github.sudhanv09.domain.model.SavingBucket

fun SavingBucket.toEntity(): SavingBucketEntity = SavingBucketEntity(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    icon = icon,
    color = color
)

fun SavingBucketEntity.toDomain(): SavingBucket = SavingBucket(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    icon = icon,
    color = color
)
